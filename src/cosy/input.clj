(ns cosy.input
  (:require [clojure.core.matrix :as cm]
            [cosy.memory :as mem]))

(def example-array
  [[{} {} {} {} [:black :circle] {} {} {}]
   [{} {} {} {} {} {} [:blue :circle] {}]
   [[:yellow :square] [:blue :square] {} {} {} {} {} {}]
   [{} [:red :circle] [:blue :circle] {} {} {} {} {}]
   [{} [:black :circls] {} {} [:black :circle] {} {} [:blue :circle]]
   [[:blue :circle]{}{}[:red :triangle]{}{}[:red :circle][:blue :quare]]
   [{} {} [:blue :circle] {} [:yellow :square] {} {} {}]
   [[:red :circle] {} {} {} {} {} {} {}]])

(swap! mem/memory assoc :stimulus example-array)

(defn build-array [shape pos-2-val]
  (let [matrix (cm/compute-matrix :ndarray shape (fn [& args] []))]
    (doseq [[[x y] value] pos-2-val]
      (cm/mset! matrix x y value))
    (cm/coerce [] matrix)))
