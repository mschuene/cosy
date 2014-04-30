(ns excercise1.core
  (:use [clojure.core.matrix]))

(def memory (atom 0))

(def example-array [[#{:blue :square} #{:hexagon :red}]
                    [#{} #{:blue :square}]])

(def example-query (fn [position] (Thread/sleep 500) (and (position :blue) (position :square))))


(defn execute-query [query array]
  (doseq [row array
        cell row]
    (and (query cell) (swap! memory inc))))

(defn perform-task [query array maximum-time]
  (let [f (future (execute-query query array))]
    (do (deref f maximum-time nil) @memory)))


(defn make-example-array [pos-2-val shape]
  (let [matrix (compute-matrix :ndarray shape (fn [& args] #{}))]
    (doseq [[[x y] value] pos-2-val]
      (mset! matrix x y value))
    matrix))
