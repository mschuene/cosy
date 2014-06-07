(ns cosy.utils)


(defn to-rectangle [region]
  (let [is (map first region)
        js (map second region)]
    [[(apply min js) (apply min is) ]
     [(apply max js) (apply min is)]
     [(apply min js) (apply max is)]
     [(apply max js) (apply max is)]]))

(defn distance
  "distance between two cells given by their coordinates in the array"
  [[x1 y1] [x2 y2]]
  (+ (Math/abs (- x1 x2)) (Math/abs (- y1 y2))))

#_(defn close-enough [region1 region2 separating-distance]
  (let [rectangles (for [rect1 region1 rect2 region2] [rect1 rect2])]
    (and (some (fn [[l r]]
                 (<= (distance l r) separating-distance)) rectangles)
         region1)))

(defn close-enough [region1 region2 separating-distance]
  (let [[[tlj1 tli1] _ _ [brj1 bri1]] (to-rectangle region1)
        [[tlj2 tli2] _ _ [brj2 bri2]] (to-rectangle region2)
        rect-region1 (for [i (range tli1 (inc bri1))
                           j (range tlj1 (inc brj1))] [i j])
        rect-region2 (for [i (range tli2 (inc bri2))
                           j (range tlj2 (inc brj2))] [i j])
        rectangles (for [rect1 rect-region1
                         rect2 rect-region2] [rect1 rect2])]
    (and (some (fn [[l r]]
                 (<= (distance l r) separating-distance)) rectangles)
         region1)))

(defn merge-regions
  ([regions] (merge-regions regions 2))
  ([regions distance]
     ;;merge regions that are close enough
     ;;merge until there is nothing to merge anymore
     (loop [regions regions]
       (let [rc (count regions)
             merged
             (reduce
              (fn [regions region]
                ;;if some region is close enough
                (if-let [reg
                         (some #(close-enough %1 region distance)
                               regions)]
                  (replace {reg (concat reg region)} regions)
                  (concat regions [region])))
              [] regions)]
         (prn "merged " merged)
         (if (= (count merged) rc)
           merged
           (recur merged))))))

(defn to-rectangle [region]
  (let [is (map first region)
        js (map second region)]
    [[(apply min js) (apply min is) ]
     [(apply max js) (apply min is)]
     [(apply min js) (apply max is)]
     [(apply max js) (apply max is)]]))
