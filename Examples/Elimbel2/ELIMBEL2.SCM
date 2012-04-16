(define bb1-order '(h y x da ob db oa sb sa g))

(define bb1-dag '(
	((h) . (0.5 0.5))
	((y) . (0.90 0.09 0.01))
	((x) . (0.92 0.075 0.005))
	((ob h) . ((0.50 0.20) (0.30 0.20) (0.20 0.60)))
	((db y h) . (((0.50 0.45) (0.30 0.25) (0.04 0.02))
				 ((0.35 0.30) (0.20 0.15) (0.03 0.015))
				 ((0.15 0.25) (0.50 0.60) (0.93 0.965))))
	((da) . (0.25 0.30 0.45))
	((oa x) . ((0.80 0.50 0.15) (0.15 0.30 0.15) (0.05 0.20 0.70)))
	((sb ob da) . (((0.30 0.55 0.78) (0.10 0.35 0.45) (0.03 0.12 0.32))
				   ((0.50 0.30 0.18) (0.30 0.50 0.35) (0.11 0.22 0.40))
				   ((0.20 0.15 0.04) (0.60 0.15 0.20) (0.86 0.66 0.28))))
	((sa oa db) . (((0.40 0.75 0.88) (0.17 0.42 0.52) (0.07 0.17 0.38))
				   ((0.48 0.20 0.07) (0.37 0.47 0.34) (0.19 0.30 0.38))
				   ((0.12 0.05 0.05) (0.46 0.11 0.14) (0.74 0.53 0.24))))
	((g sb sa) . (((0.50 0.35 0.15) (0.35 0.50 0.35) (0.15 0.35 0.50))
				  ((0.50 0.65 0.85) (0.65 0.50 0.65) (0.85 0.65 0.50))))
))

(define bb1-evidence '(
	((h) . (0 1))
))

(load "elimbel")
(define display-mode 0)
;(get-belief bb1-order bb1-dag bb1-evidence)
(get-belief '(h y x da ob db oa sb sa g) bb1-dag bb1-evidence)
(get-belief '(g h y x da ob db oa sb sa) bb1-dag bb1-evidence)
(get-belief '(sa g h y x da ob db oa sb) bb1-dag bb1-evidence)
(get-belief '(sb sa g h y x da ob db oa) bb1-dag bb1-evidence)
(get-belief '(oa sb sa g h y x da ob db) bb1-dag bb1-evidence)
(get-belief '(db oa sb sa g h y x da ob) bb1-dag bb1-evidence)
(get-belief '(ob db oa sb sa g h y x da) bb1-dag bb1-evidence)
(get-belief '(da ob db oa sb sa g h y x) bb1-dag bb1-evidence)
(get-belief '(x da ob db oa sb sa g h y) bb1-dag bb1-evidence)
(get-belief '(y x da ob db oa sb sa g h) bb1-dag bb1-evidence)

;;; OUTPUT: belief for the last node of the order

; Belief-l(l) = (.16886514578951503 .831134854210485) -- done

;; NOTATIONS:
;;
;;    ((a b c) . (...)) stands for P(a|b^c) = (...) or Lambda(a,b,c)
;;    A random variable 'c' gets the values c1, c2, ...
;;    Suppose that we have
;;    ((a b c) . (
;;          (
;;           (0.70 0.56 0.03)
;;           (0.23 0.23 0.67)
;;          )
;;        
;;           (0.30 0.44 0.97)
;;           (0.77 0.77 0.33)
;;          )
;;    ))
;;    then a gets 2 values (a1 and a2), b gets
;;    2 values (b1 and b2), c gets 3 values (c1, c2 and c3)
;;    and, for instance, P(a2|b1^c3) = 0.97 (resp. Lambda(a2,b1,c3) = 0.97)
;;

