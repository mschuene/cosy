(ns cosy.gen
  (:require [clojure.test.check :as tc]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [cosy.input :as in]))


(defn gen-array []
  (let [x (gen/choose 18 20)
        y (gen/choose 18 20)
        s (gen/frequency [[15 (gen/elements
                              [:circle :square
                               :triangle])]
                          [1 (gen/return :obscured)]])
        c (gen/elements [:black :blue :red :yellow :green])
        elemc (gen/choose 15 200)]
    (as-> (gen/tuple elemc x y) gen
          (gen/bind gen (fn [[ec x y]]
                          (gen/tuple
                           (gen/vector
                            (gen/tuple (gen/choose 0 (dec x))
                                       (gen/choose 0 (dec y))
                                                  s c) ec)
                           (gen/return x) (gen/return y))))
          (gen/fmap (fn [[v x y]]
                      (->> (for [[x y s c] v]
                             [[x y] [(if (= :obscured s) :grey c) s]])
                           (into {})
                           (in/build-array [x y]))) gen))))

(defn generate-next-array []
  (gen/sample (gen-array) 1))

;;todo work more closely together with memory
;;goodby pureness hello human mind
