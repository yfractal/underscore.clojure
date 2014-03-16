(ns underscore.core)

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

(defn _map
  ([f coll]
     (_map f coll 0))
  ([f coll i]
     (let [arg-num (num-of-arguments f)]
       (cond (= arg-num 0) (f)
             (= arg-num 1) (map f coll)
             (= arg-num 2) (if (empty? coll)
                             nil
                             (cons (f (first coll) i) (_map f (rest coll) (inc i))))))))

(defn _reduce
  ([f val coll]
     (_reduce f val coll 0))
  ([f val coll i]
     (let [arg-num (dec (num-of-arguments f))]
       (cond (= arg-num 0) (f)
             (= arg-num 1) (reduce f val coll)
             (= arg-num 2) (if (empty? coll)
                             val
                             (let [val* (f val (first coll) i)]
                               (_reduce f val* (rest coll) (inc i))))))))
