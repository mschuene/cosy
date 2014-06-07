(ns excercise3.core
  (:use [clojure.core.matrix]))

(def memory (atom {:picture nil
                   :task nil
                   :strategy nil
                   :results
                   {:counted 0 :seen 0 :ec 0}
                   :time-available 0}))

(defn execute-query-simple [query array]
  (doseq [row array
          cell row]
    (and (query cell) (swap! memory update-in [:results :counted] inc))))

(defn make-example-array [pos-2-val shape]
  (let [matrix (compute-matrix :ndarray shape (fn [& args] #{}))]
    (doseq [[[x y] value] pos-2-val]
      (mset! matrix x y value))
    matrix))

(defn initialize-memory [defaults]
  (doseq [[k v] defaults]
     (swap! memory assoc-in [:results k] v)))

(defn execute-query-advanced [query array]
  (let [ec (ecount array)
        _ (initialize-memory {:seen 0 :ec ec :counted 0})]
    (doseq [row array
            cell row]
      (swap! memory update-in [:results :seen] inc)
      (and (query cell) (swap! memory update-in [:results :counted] inc)))))

(defn guess [{:keys [seen ec counted]}]
  (prn "guess ")
  {:counted (long (/ counted (if (= (/ seen ec) 0) 1 (/ seen ec))))})

(def strategies
  {:simple {:process execute-query-simple
            :guess identity}
   :advanced {:process execute-query-advanced
              :guess guess}})

(defn choose-strategy [features time-available]
  ;;for now simply choose basic strategy
  (swap! memory assoc :strategy (get strategies :advanced)))

(defn set-timer [time-available]
  (swap! memory assoc :time-available time-available))

(defn describe-task [features time-available]
  (swap! memory assoc :task (fn [position]
                              (do (Thread/sleep 500) ;;timeout for tests
                                  (every? position features))))
  (choose-strategy features time-available)
  (set-timer time-available))

(defn perform-task []
  (let [{:keys [strategy task picture time-available]} @memory
        f (future ((:process strategy) task picture))]
    (if (= :timeout (deref f time-available :timeout))
      (-> ((:guess strategy) (:results @memory)) :counted)
      (-> @memory :results :counted))))

(defn see-picture [picture]
  ;;assume picture is already in internal representation
  (swap! memory assoc :picture picture)
  (perform-task))

(def example-array [[#{:blue :square} #{:hexagon :red}]
                    [#{} #{:blue :square}]])

(def example-task [:blue :square])

