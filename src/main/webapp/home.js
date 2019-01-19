/*
    This is the javascript file that essentially runs all of our backend.
    All of our API requests, as well as our routing is done through this file.
 */

//  Initializes our angular application, in which we include the below libraries for routing
//  and other functions
var app = angular.module('clinicalTrialApp', ['ui.router', 'ngMaterial', 'ngAnimate']);

/**
 * This is the setup for our ui routing component, it allows us to define our roots and states of our
 * application. We pass the url and route providers in order to dynamically change url parameters, as well as
 * $window in order to directly access the window of our page.
 *
 * Each 'state' below offers us a potential landing page that we can access depending on a trigger or an
 * action in the application.
 *
 * In a state below, you have the state name, in which it will be referenced. You have the url, which is the actual
 * url you will reference. The template url, which is the name of the file you want to pull the HTML code from. And lastly,
 * you can call controller that you made in order to access a group of functions you defined, almost like inheriting a class.
 * You can add functions right in the state if you want, or call them, both are done below.
 */
app.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider, $window) {

    $stateProvider
        .state('home', {
            url: '/home',
            templateUrl: 'home.html'
        })

        .state('searchBar', {
            url: '/searchBar',
            templateUrl: 'searchBar.html',
            controller: 'autoCompleteController'
        })

        .state('recruitingTrials', {
            url: '/recruitingTrialList?condition',
            templateUrl: 'recruitingByCondition.html',
            controller: function($scope, $http, $stateParams) {
                $scope.condition = $stateParams.condition;
                $http.get("http://localhost:8080/clin/clinical/getRecruitingTrialsByCondition/" + $scope.condition).then(function (response) {
                    $scope.trials = response.data;
                });
            }

        })

        .state('allTrials', {
            url: '/fullTrialList?condition',
            templateUrl: 'fullByCondition.html',
            controller: function ($scope, $http, $stateParams) {
                $scope.condition = $stateParams.condition;
                $http.get("http://localhost:8080/clin/clinical/getTrialsByCondition/" + $scope.condition).then(function(response) {
                    $scope.trials = response.data;
                });
            }

        })

        .state('viewTrials', {
            url: '/viewTrials?nct',
            templateUrl: 'trialInfo.html',
            controller: function ($scope, $http, $stateParams) {
                $scope.nct = $stateParams.nct;
                $http.get("http://localhost:8080/clin/clinical/getFullTrialInfo/" + $scope.nct).then(function (response) {
                    $scope.trialInfo = response.data;

                });

                $scope.filterCondition = function(condition) {
                    var list = JSON.stringify(condition);
                    return list.replace(/{/g, '').replace(/"/g, ' ').replace(/@value/g, '').replace(/:/g, '').replace(/}/g, '').replace('[', '').replace(']', '');
                };

                $scope.goBack = function() {
                    $window.history.back();
                };

                $scope.hideConflicts = function (nct) {
                    $scope.empty = "";
                    $http.get("http://localhost:8080/clin/clinical/getConflicts/" + nct).then(function (response) {
                        $scope.empty = response.data;

                        if ($scope.empty.length === 0) {
                            $scope.empty = false;
                        }
                    });
                }

            }
        })

        .state('findConflicts', {
            url: '/findConflicts?nct',
            templateUrl: 'viewConflicts.html',
            controller: function($scope, $http, $stateParams) {
                $scope.nct = $stateParams.nct;
                $http.get("http://localhost:8080/clin/clinical/getConflicts/" + $scope.nct).then(function (response) {
                    $scope.conflictInfo = response.data;
                });



            }
        })

        .state('viewTrials.map', {
            templateUrl: 'scratchFiles/map.html'
        })

        /**
         * As you can see below, we call a view, which is basically a sub section of a state. So if we are
         * in the fullEligCrit state, we want to call another view into the state without leaving the view.
         */
        .state('viewTrials.fullEligCrit', {
            views: {
                'fullCrit': {
                    templateUrl: 'fullEligCritView.html',
                    controller: function ($scope, $http, $stateParams) {
                        $scope.nct = $stateParams.nct;
                        $http.get("http://localhost:8080/clin/clinical/getFullTrialInfo/" + $scope.nct).then(function (response) {
                            $scope.trialInfo = response.data;
                        });
                    }
                }
            }
        })

        .state('viewTrials.descr', {
            views: {
                'detailedDescr': {
                    templateUrl: 'detailedDescrView.html',
                    controller: function ($scope, $http, $stateParams) {
                        $scope.nct = $stateParams.nct;
                        $http.get("http://localhost:8080/clin/clinical/getFullTrialInfo/" + $scope.nct).then(function (response) {
                            $scope.trialInfo = response.data;
                        });
                    }
                }
            }
        });

    // This is the default page to land on if all else fails
    $urlRouterProvider.otherwise('/searchBar');
}]);


/**
 * Controllers allow you to section off a particular job into one area so you can reference these
 * functions exclusively to a state or view. Almost like having a specific class for a job.
 */
app.controller('autoCompleteController', function($scope, $http) {

    $scope.conditionList = [];
    var data = "";
    var temp = [];

    // Gets all conditions and then formats them to be used in our autocomplete
    $http.get('http://localhost:8080/clin/clinical/getAllConditions').then(function(response) {
          data = response.data.toString();
          temp = data.toString().replace("[", "").replace("]", "").split(",");
          console.log(temp);
    });


    /*
        Filters the value that is being typed into the search bar in order to
        keep checking the autocomplete search.
     */
    $scope.cname = function(string) {

        $scope.hidethis = false;
        $scope.hideBox = true;


        var output = [];

        angular.forEach(temp, function(condition) {

            if (condition.toLowerCase().indexOf(string.toLowerCase()) >= 0) {

                output.push(condition);

                if (condition === "") {
                    $scope.hideBox = false;
                }

            }
        });

        $scope.filterCondition = output;

        if (string === "") {
            $scope.hideBox = false;
            $scope.disable = true;
        }

    };

    /*
        Fills the search bar with the value when found. Also triggers the dropdown box to
        hide when the value is found.
     */
    $scope.fillInputBox = function(string) {

        $scope.condition = string;

        $scope.hidethis = true;
        $scope.hideBox = false;
        $scope.disable = false;
    };

    $scope.hideConflicts = function(nct) {
        var conflicts = "";

        $http.get("http://localhost:8080/clin/clinical/getConflicts/" + nct).then(function (response) {
            conflicts = response.data;
            console.log(conflicts);
            if (conflicts === null) {
                return true;
            }
        });
    }

});
