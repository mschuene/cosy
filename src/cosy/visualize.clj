(ns cosy.visualize
  (:require [quil.core :refer :all]
            [cosy.input :as in]
            [cosy.memory :as mem]))

(defn setup []
  (smooth)                         
  (frame-rate 24)                  
  (background 200))

(def cellwidth 30)
(def cellheight 30)

(def rgb-color {:red [255 0 0]
                :grey [100 100 100]
                :blue [0 0 255]
                :green [0 255 0]
                :yellow [255 255 0]
                :black [0 0 0]})

(defn get-rgb-color [cell]
  (get rgb-color (first cell) [255 255 255]))

(def draw-funcs
  {:square #(rect (+ 2 %1) (+ 2 %2)
                  (- cellwidth 4)
                  (- cellheight 4))
   :circle #(ellipse (+ 15 %1) (+ 15 %2)
                     (- cellwidth 5)
                     (- cellheight 5))
   :triangle #(triangle (+ %1 15) %2
                        (+ %1 5) (+ %2 25)
                        (+ %1 25) (+ %2 25))
   :obscured #(do 
                  (triangle (+ %1 15) %2
                               (+ %1 5) (+ %2 25)
                               (+ %1 25) (+ %2 25))
                  (triangle (+ %1 15) (+ %2 30)
                            (+ %1 5) (+ %2 5)
                            (+ %1 25) (+ %2 5)))})

(defn draw-shape [shape x y]
  ((get draw-funcs shape (constantly nil)) x y))

(def visual-input (atom in/example-array))


(defn visualize-rectangular-region [region]
  (begin-shape :lines)
  (let [[top-l top-r bottom-l bottom-r] region]
    ;;draw four lines
    (let [[x y] top-l]
      (vertex (* cellwidth x) (* cellheight y)))
    (let [[x y] top-r]
      (vertex (* cellwidth (inc x)) (* cellheight y)))
    (let [[x y] top-r]
      (vertex (* cellwidth (inc x)) (* cellheight y)))
    (let [[x y] bottom-r]
      (vertex (* cellwidth (inc x)) (* cellheight (inc y))))
    (let [[x y] bottom-r]
      (vertex (* cellwidth (inc x)) (* cellheight (inc y))))
    (let [[x y] bottom-l]
      (vertex (* cellwidth x) (* cellheight (inc y))))
    (let [[x y] bottom-l]
      (vertex (* cellwidth x) (* cellheight (inc y))))
    (let [[x y] top-l]
      (vertex (* cellwidth x) (* cellheight y))))
  (end-shape))

(defn visualize-regions [regions]
  (doseq [[col colregions] regions
          region colregions]
   
    (fill (apply color (get-rgb-color [col])))
    (visualize-rectangular-region region)))

(defn visualize-array []
  ;;each cell will be 25x25 pixel big
  (let [array (:stimulus @mem/memory)
        regions (:color-regions @mem/memory)]
    (fill 200)
    (rect 0 0 600 600)
    (doseq[[i y] (map-indexed
                  vector (range 0 (* cellwidth (count array))
                                cellwidth))
           [j x] (map-indexed
                  vector (range 0 (* cellheight (count (first array)))
                                cellheight))]
      (fill (apply color (get-rgb-color (get-in array [i j]))))
      (draw-shape (second (get-in array [i j])) x y))
    (visualize-regions regions)))

(defn boundaries-of-region [region])

(defsketch example                 
  :title "Clojure is back!" 
  :setup setup                     
  :draw visualize-array
  :size [600 600])           

#_(let [gen (first (cosy.gen/generate-next-array))]
                   (swap! mem/memory assoc 
                          :stimulus gen
                          :color-regions 
                          (color-regions gen [:red])))

(def example (:stimulus @mem/memory))
