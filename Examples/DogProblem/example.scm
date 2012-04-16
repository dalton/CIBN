;; example.scm
;; Example for S-ElimBel - Copyright Nicolas Thiery, November 95
;; (Requires S-ElimBel at elimbel.scm)
;;
;; Permission to use, copy, and distribute this software
;; for any purpose and without fee is hereby granted,
;; provided that this notice an the name of the author
;; appear in all copies. This software is provided "as is"
;; without express or implied warranty.
;;
;; An example of Bayesian Network propagation of
;; belief using the Elim-Bel Algorithm (R. Dechter)
;; implemented in MIT Scheme
;;
;; This example was taken from "Bayesian Network w/o Tears",
;; page 53. For more information,
;; see http://www.spaces.uci.edu/thiery/elimbel
;;
;; USAGE: (load "example.scm")
;;

;; 1. We specify the problem:

(define example-order '(d h b f l))

(define example-dag '(
	((b) . (0.01 0.99))
	((f) . (0.15 0.85))
	((l f) . ((0.60 0.05) (0.40 0.95)))
	((d b f) . (((0.99 0.97) (0.90 0.30)) ((0.01 0.03) (0.10 0.70))))
	((h d) . ((0.70 0.01) (0.30 0.99)))
))

(define example-evidence '(
	((h) . (1 0))
	((b) . (0 1))
))

;; 2. We load the ElimBel program:

(load "elimbel.scm")

;; 3. We run the computation for the problem:

(get-belief example-order example-dag example-evidence)

;; Finally, we get the belief for the node l:
;
; Belief-l(l) = (.16886514578951503 .831134854210485) -- done
