(ns resume.main
  (:require [clojure.java.io :as io]
            [hiccup.core :as h]
            [garden.core :as css]
            [garden.units :as units]))

(def ndash "&ndash;")
(def mdash "&mdash;")

(let [section-item-heading-margin-top {:margin-top (units/px 4)}]
  (def styles
    [[:body {:font-family ["'Roboto'" 'sans-serif]
             :font-size (units/px 15)
             :line-height (units/em 1.5)
             :padding (units/em 1.5)
             :color "#222222"}]
     [:h1 {:font-size (units/em 2.5)
           :margin-block-end (units/em 0.2)
           :color "#444444"
           :letter-spacing (units/em 0.025)}]
     [:h2 {:margin-block-end (units/em 0.15)
           :padding-top (units/em 0.45)
           :padding-bottom (units/em 0.1)
           :padding-left (units/em 0.5)
           :margin-left (units/em -0.5)
           :font-size (units/em 1)
           :font-weight 700
           :line-height (units/em 1)
           :text-transform 'uppercase
           :letter-spacing (units/em 0.05)
           :background-color "#F4F4F4"}]
     [:ul {:margin-block-start (units/em 0)
           :margin-block-end (units/em 0)}]
     [:a {:color "#686868"}]
     [".company:not(:first-of-type)" section-item-heading-margin-top]
     [:.company-title {:font-style 'italic}]
     [".project:not(:first-of-type)" section-item-heading-margin-top]
     [".education:not(:first-of-type)" section-item-heading-margin-top]
     [:.code {:font-family ["'Roboto Mono'", 'monospace]}]]))

(defn date [{:keys [start end point-in-time]}]
  [:span
   (if (and start end)
     (str start " " ndash " " end)
     point-in-time)])

(defn in-line-date [& dates]
  [:span {:style (css/style {:font-size (units/px 14)
                             :color "#686868"})}
   "(" (interpose ", " dates) ")"])

(defn section [heading & children]
  (into [:section
         [:h2 {:style (css/style {:border-bottom [[(units/px 1) 'solid "#EEEEEE"]]})}
          heading]]
        children))

(def section-item-heading-style {:display 'flex
                                 :justify-content 'space-between
                                 :font-weight 500})

(defn company-heading
  ([company location date]
   [:div.company {:style (css/style section-item-heading-style)}
    [:span
     [:span company]
     (str " " mdash " ")
     [:span location]]
    date])
  ([company location title date]
   [:div.company
    [:div {:style (css/style section-item-heading-style)}
     [:span
      [:span company]
      (str " " mdash " ")
      [:span location]]
     date]
    [:div.company-title title]]))

(defn company-position-heading [title]
  [:div.company-title title])

(defn project-heading
  ([title date]
   [:div.project {:style (css/style section-item-heading-style)}
    [:span title]
    date])
  ([title outcome date]
   [:div.project {:style (css/style section-item-heading-style)}
    [:span
     [:span title]
     (str " " mdash " ")
     [:span outcome]]
    date]))

(defn education-entry [degree school date]
  [:div.education {:style (css/style section-item-heading-style)}
   [:span
    [:span degree]
    (str " " mdash " ")
    [:span school]]
   date])



(defn heading []
  [:div {:style (css/style {:display 'flex
                            :justify-content 'space-between
                            :align-items 'baseline})}
   [:section
    [:h1 "Cort Spellman"]
    [:div
     [:span "spellman.cort@gmail.com"]
     [:span {:style (css/style {:margin-left (units/em 1)})}
      "979-436-2192"]]]
   [:section
    [:div [:a {:href "https://github.com/spellman"} "github.com/spellman"]]
    [:div [:a {:href "https://www.linkedin.com/in/cort-spellman-a2718313/"} "linkedin.com/in/cort-spellman-a2718313/"]]]])

(defn key-skills []
  (let [skills ["Understanding problems"
                "Programming (functional, scripting, OO)"
                "Clojure"
                "ClojureScript"
                "Writing"
                "Coaching"]]
    (section
     "Key Skills"
     (into [:ul {:style (css/style {:display 'flex
                                    :justify-content 'space-between
                                    :padding (units/em 0)})}]
           (map (fn [skill] [:li {:style (css/style {:display 'block})} skill])
                skills)))))

(defn usage-number [s]
  [:span {:style (css/style {:font-style 'italic})}
   "(" s")"])

(defn work-experience []
  (section
   "Work Experience"
   (company-heading
    "Roomkey"
    "Charlottesville, VA and remote from Austin, TX"
    "Software Engineer"
    (date {:start "Jan 2018" :end "present"}))
   [:ul
    [:li "Propose, build, and test features and implement redesign and conversion from React, Redux to ClojureScript, Reagent on"
     [:ul
      [:li [:a {:href "http://roomkey.com/"} "roomkey.com"] " single-page application " (usage-number "38k visitors/day")]
      [:li "Interstitial pop-under that displays on Roomkey’s partner’s sites (Marriott, Hilton, Hyatt, etc.) and is responsible for 90%+ of Roomkey’s revenue " (usage-number "230k visitors/day")]]]
    [:li [:span "Propose and build features and handle maintenance and deployment of "
          [:a {:href "https://www.roomkey.com/scout"} "Scout by Roomkey"]
          " browser extension. " (usage-number "51k total downloads, 29k average weekly users at peak")]]
    [:li "Built features for internal admin tool and corrected a deficient audit-trail data model and diff GUI, using ClojureScript, Reagent, Re-frame, Clojure, and Datomic Cloud."]
    [:li "Maintained browser-automation-based tests of Scout by Roomkey browser extension and then proposed and built lower-maintenance method of testing."]
    [:li "Helped expand Scout by Roomkey Chrome-only browser extension to Firefox."]]

   (company-heading
    "Parallactech"
    "Arcadia, OK"
    (date {:start "Mar 2016" :end "Dec 2017"}))
   (company-position-heading "Front-End Developer")
   [:ul
    [:li "Designed and built TypeScript front-end of Ionic/Angular web and mobile app for construction job-site management."]
    [:li "Implemented Elm architecture in Ionic/Angular using RxJS and ported Elm JSON decoders to Typescript in order to validate incoming JSON."]]
   (company-position-heading "Full-Stack Developer")
   [:ul
    [:li "Refactored and added features to a web application that tracked fracking water from source to transit to fracking site in order to enable sale of the app to a larger customer audience."]
    [:li "Built features across the stack: Ext JS 4 front-end, C# web server, SQL Server queries and stored procedures."]]))

(defn projects []
  (section
   "Select Projects and Open-Source Contributions"
   (project-heading
    "Auto-scorekeeping system for 2018 USA Powerlifting Raw National Championship"
    (date {:point-in-time "Oct 2018"}))
   [:ul
    [:li "Clojure program that was used in live sporting event in place of human scorekeeper in order to expedite the event."]
    [:li [:a {:href "https://github.com/spellman/drl-control-liftingcast"}
          "https://github.com/spellman/drl-control-liftingcast"]]]

   (project-heading
    [:span "TypeScript type definitions for Ramda: Update " [:span.code "reduceRight"] " function"]
    "PR merged"
    (date {:start "Aug 2017" :end "Sep 2017"}))
   [:ul
    [:li [:a {:href "https://github.com/DefinitelyTyped/DefinitelyTyped/pull/18798"}
          "https://github.com/DefinitelyTyped/DefinitelyTyped/pull/18798"]]
    [:li "Exposed a bug in the TypeScript compiler."]]

   (project-heading
    [:span "clojure.algo.generic: Implement " [:span.code "abs"] " and " [:span.code "round"] " functions for additional number types"]
    "patch merged"
    (date {:point-in-time "Aug 2015"}))
   [:ul
    [:li [:a {:href "https://clojure.atlassian.net/browse/ALGOG-12"}
          "https://clojure.atlassian.net/browse/ALGOG-12"]]
    [:li [:a {:href "https://github.com/clojure/algo.generic/commit/d90c2ba1fdb01306bd0fc1d6434527a6d549f73f"}
          "https://github.com/clojure/algo.generic/commit/d90c2ba1fdb01306bd0fc1d6434527a6d549f73f"]]]))

(defn education []
  (section
   "Education"
   (education-entry
    "M.S. Mathematics"
    "Texas A&M University, College Station, TX"
    (date {:point-in-time "Jul 2011"}))

   (education-entry
    "B.S. Mathematics, B.S. Physics"
    "Texas Christian University, Fort Worth, TX"
    (date {:point-in-time "May 2005"}))
   [:span "Phi Beta Kappa honor society"]))

(defn additional-experience []
  (section
   "Additional, Communication, and Teaching Experience"
   [:ul
    [:li [:span
          "Texas A&M Powerlifting head coach "
          (in-line-date (date {:start "2006" :end "2013"}))
          " and USA Powerlifting Junior National Team assistant coach "
          (in-line-date (date {:start "2008" :end "2009"})
                        (date {:start "2011" :end "2013"})
                        (date {:point-in-time "2018"}))
          "."]]
    [:li [:span
          "Taught powerlifting and/or strength-training fundamentals to several coworkers at Roomkey. "
          (in-line-date (date {:start "Jan 2018" :end "Oct 2019"}))]]
    [:li [:span
          "Worked with endocrinologist to build expert system to enable non-diabetes-specialist physicians to treat type II diabetes like a specialist. The web-based system was a Ruby on Rails app with a Clojure back-end. "
          (in-line-date (date {:start "2013" :end "2016"}))]]
    [:li [:span
          "Graduate teaching assistant for mathematics at Texas A&M and self-employed tutor in mathematics, physics, and statistics for high-school, undergrad, and grad students. "
          (in-line-date (date {:start "2006" :end "2009"})
                        (date {:start "2011" :end "2013"}))]]
    [:li [:span
          "Worked full-time as a front-end developer at Reynolds & Reynolds "
          [:span {:style (css/style {:font-size (units/px 14)
                                     :color "#686868"})}
           "("
           [:a {:href "https://www.linkedin.com/company/the-reynolds-and-reynolds-company"}
            "https://www.linkedin.com/company/the-reynolds-and-reynolds-company"]
           ")"]
          " "
          (in-line-date (date {:start "2009" :end "2011"}))
          " while completing master's degree."]]]))



(defn page []
  (h/html
   [:html {:lang "en"}
    [:head [:meta {:http-equiv "Content-Type" :content "text/html; charset=UTF-8"}]
     [:title "Cort Spellman"]
     [:link {:href "https://fonts.googleapis.com/css2?family=Roboto+Mono&family=Roboto:ital,wght@0,400;0,500;0,700;1,400&display=swap" :rel "stylesheet"}]
     [:style (css/css {:pretty-print? false} styles)]]
    [:body
     (heading)
     (key-skills)
     (work-experience)
     (projects)
     (education)
     (additional-experience)]]))



(defn write []
  (let [f "out/resume.html"]
    (io/make-parents f)
    (spit f (page))))



(write)

(comment

 (write)

 (page)

 )
