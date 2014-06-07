(ns cosy.focus
  (:require [cosy.input :as in]
            [clojure.set :as set]
            [clojure.core.matrix :as cm]))

(def focus-diam 2)

(def example-region
  [[[:red :circle] [:red :circle] [:red :circle] [] [:red :circle]]
   [[:red :circle] [:red :circle] [:red :circle] [] [:red :circle]]
   [[:red :circle] [:red :circle] [:red :circle] [] [:red :circle]]])

;;isnt this merge regions with separation distance 1?
;;time for util namespace

(defn matches? [[color shape] color-pred? shape-pred?]
  (and color (color-pred? color) (shape-pred? shape)))

(defn adjacent-cells [region color-pred? shape-pred? [x y]]
  (let [directions (for [dx [-1 0 1] dy [-1 0 1]] [dx dy])]
    (->> directions
         (#(do (prn %) %))
         (map (fn [[dx dy]]
                   (if (and (> (count region) (+ x dx) -1)
                            (> (count (first region)) (+ y dy) -1))
                     [(get-in region [(+ x dx) (+ y dy)])
                      [(+ x dx) (+ y dy)]]
                     [[] []])))
         (#(do (prn %) %))
         (filter (comp #(matches? % color-pred? shape-pred?) first))
         (map second)
         (into #{}))))

(defn get-cluster [region color-pred? shape-pred? position]
  (loop [adj #{position}]
    (let [nadj (apply set/union
                      (map #(adjacent-cells
                             region
                             color-pred?
                             shape-pred?
                             %) adj))]
      (if (= (count adj) (count nadj))
        adj
        (recur  nadj)))))

(defn recognize-square [region color-pred? shape-pred? [i j]]
  (let [current-cell (get-in region [i j])]
    ;;assume it is the top left cell of a square - sound.
    (loop [size 2
           matching-cluster nil]
      (let [cluster (for [i (range i (+ i size))
                         j (range j (+ j size))]
                      [i j])]
        (if (every?
             #(matches? (get-in region %) color-pred? shape-pred?)
             cluster)
          (recur (inc size) cluster)
          (if-not (= size 2) ;;no square found
            [matching-cluster {:tpye :square
                          :size (dec size)
                          :cluster matching-cluster}]
            )
          )))))

(defn recognize-cross [region color-pred? shape-pred? [i j]]
  (let [cluster
        #{[i j] [(inc i) (dec j)] [(inc i) j]
          [(inc i) (inc j)][(+ i 2) (inc j)]}]
    (when (every? #(matches? (get-in region %) color-pred? shape-pred?)
                  cluster)
      [cluster {:type :cross}])))

(defn recognize-line [region color-pred? shape-pred? [i j]]
  (let [horizontal
        (loop [j j cluster #{[i j]}]
          (if (matches? (get-in region [i j]) color-pred? shape-pred?)
            (recur (inc j) (conj cluster [i j]))
            cluster))
        vertical
        (loop [i i cluster #{[i j]}]
          (if (matches? (get-in region [i j]) color-pred? shape-pred?)
            (recur (inc i) (conj cluster [i j]))
            cluster))
        recognized (if (>= (count horizontal) (count vertical))
                     horizontal vertical)]
    (when (>= (count recognized) 2)
      [recognized {:type :line
                   :length (count recognized)
                   :cluster recognized}])))

(defn recognize-point [region color-pred? shape-pred? position]
  [#{position} {:type :point
                :cluster #{position}}])

;;return recognized objects or nil
(def visual-routines
  [recognize-square
   recognize-cross
   recognize-line
   recognize-point
   ])

(defn apply-visual-routines [region color-pred? shape-pred? position]
  (some #(% region color-pred? shape-pred? position)
        visual-routines))
;;ok next approach - don't return whole cluster but for each position
;;try in the visual routines if there is a object

(defn next-location [region current-location looked-at]
  (let [rowc (count region)
        colc (count (first region))
        _ (prn "region " region)
        [r c] current-location
        nl (cond
            (> colc (inc c)) [r (inc c)]
            (> rowc (inc r)) [(inc r) 0]
            :else nil)]
    (if ((set looked-at) nl)
      (recur region nl looked-at)
      nl)))

(defn scan-region [region color-pred? shape-pred?]
  (loop [position [0 0] looked-at #{} recognized-objects []]
    ;;search for matching-cell
    (prn "current-position " position
         "looked at " looked-at
         "recognized-objects " recognized-objects)
    (cond
     (nil? position) recognized-objects
     (matches? (get-in region position) color-pred? shape-pred?)
     (let [_ (prn "found something -look for visual-routines")
           [visited recognized]
            (apply-visual-routines
             region color-pred? shape-pred? position)
            looked-at (set/union looked-at visited)]
        (recur (next-location region position looked-at)
               looked-at
               (conj recognized-objects recognized)))
      :else (recur (next-location region position looked-at)
                   looked-at recognized-objects))))
                                            
   

(defn color-region-to-region [color-region visual-stimulus]
  (let [[topl _ _ bottomr] color-region]
    (cm/coerce
     [] (cm/select visual-stimulus
                   (range (second topl) (inc (second bottomr)))
                   (range (first topl) (inc (first bottomr)))))))

(defmulti elemcount :type)

(defmethod elemcount :point [_] 1)
(defmethod elemcount :cross [_] 5)
(defmethod elemcount :square [sq]
  (* (:size sq) (:size sq)))
(defmethod elemcount :line [l]
  (:length l))

(defn sum-elements-in-region [scanned-region]
  (reduce #(+ %1 (elemcount %2)) 0 scanned-region))

(defn count-region [stimulus color-region color-pred? shape-pred?]
  (-> color-region
      (#(do (prn "cr " %) %))
      (color-region-to-region stimulus)
      (#(do (prn "regs " %) %))
      (scan-region color-pred? shape-pred?)
      (#(do (prn "scanned " %) %))
      sum-elements-in-region))
