(ns cosy.peripheral
   (:require [cosy.input :as in]
             [cosy.memory :as mem])
     (:use [cosy.utils]))

(defn obscured? [cell]
  (= :obscured (second cell)))

(defn coordinates [stimuli pred?]
  (for [[i row] (map-indexed (fn [& v] v) stimuli)
        [j cell] (map-indexed (fn [& v] v) row)
        :when (or (pred? cell) (obscured? cell))]
    [i j]))


(defn regions [type stimuli to-look-for]
  ;; first find all coordinates of objects with the intersting-color
  (->>
   (for [feature to-look-for]
     [feature
      (->> (coordinates stimuli #(= feature ((if (= type :color)
                                               first second) %))) 
           (map vector)        ;initial regions
           merge-regions       ;;merge them
           (map to-rectangle))]) ;; only rectangles are supported
   (into {})))

(defn peripheral-view
  "type can be :color or :shape"
  [type for-what]
  (let [stimulus (:stimulus @mem/memory)]
    (case type
      :color (color-regions)))
  )
