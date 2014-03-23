(ns underscore.core)

(def NO-ARG 0)
(def NO-INDEX 1)
(def WITH-INDEX 2)

(defmacro _dispatch2 [f-with-1-argument-form f-with-2-argument-form]
  `(try
     (doall
      ~f-with-1-argument-form) ; force throw exception in right place
     (catch clojure.lang.ArityException e#
       ~f-with-2-argument-form)))

(defmacro _dispatch-val [f-with-1-argument-form f-with-2-argument-form]
  `(try
     ~f-with-1-argument-form ; a for will get a val directly,ex recuce
     (catch clojure.lang.ArityException e#
       ~f-with-2-argument-form)))

(defn _map
  ([f coll]
     (_map f coll 0))
  ([f coll i]
       (_dispatch2 (map f coll)
                   (if (empty? coll)
                     nil
                     (cons (f (first coll) i) (_map f (rest coll) (inc i)))))))

(defn _reduce
  ([f val coll]
     (_reduce f val coll 0))
  ([f coll]
     (_dispatch-val (reduce f coll)
                 (let [val (f (first coll) (second coll) 0)
                       remain (rest (rest coll))]
                   (_reduce f val remain 1))))
  ([f val coll i]
     (_dispatch-val (reduce f val coll)
                    (if (empty? coll)
                      val
                      (let [val* (f val (first coll) i)]
                        (_reduce f val* (rest coll) (inc i)))))))
