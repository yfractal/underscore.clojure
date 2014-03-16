(ns underscore.core)

(def NO-ARG 0)

(def NO-INDEX 1)
(def WITH-INDEX 2)

(defn reverse-join [pattern string]
  (clojure.string/join string pattern))

(defn- get-full-name [f]
  (-> f str (.replaceAll "_"  "-") (clojure.string/split #"[\$@]") butlast (reverse-join "/")))

(defn get-fun [full-name]
  (->> full-name (str "#'") load-string))

(defn arglists [fun]
  (let [meta* (meta  fun)]
    (:arglists meta*)))

(defn count-argument [arglists]
  (-> arglists first count))

(defn num-of-arguments [f]
  (-> f get-full-name get-fun arglists count-argument))

(defn num-of-arguments-with-init-value [f]
  (dec (num-of-arguments f)))

(defn _map
  ([f coll]
     (_map f coll 0))
  ([f coll i]
     (let [arg-num (num-of-arguments f)]
       (cond (= arg-num NO-ARG) (f)
             (= arg-num NO-INDEX) (map f coll)
             (= arg-num WITH-INDEX) (if (empty? coll)
                             nil
                             (cons (f (first coll) i) (_map f (rest coll) (inc i))))))))

(defn _reduce
  ([f val coll]
     (_reduce f val coll 0))
  ([f coll]
     (let [arg-num (num-of-arguments-with-init-value f)]
       (cond (= arg-num NO-ARG) (f)
             (= arg-num NO-INDEX) (reduce f coll)
             (= arg-num WITH-INDEX) (let [val (f (first coll) (second coll) 0)
                                 remain (rest (rest coll))]
                             (_reduce f val remain 1)))))
  ([f val coll i]
     (let [arg-num (num-of-arguments-with-init-value f)]
       (cond (= arg-num NO-ARG) (f)
             (= arg-num NO-INDEX) (reduce f val coll)
             (= arg-num WITH-INDEX) (if (empty? coll)
                             val
                             (let [val* (f val (first coll) i)]
                               (_reduce f val* (rest coll) (inc i))))))))
