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

