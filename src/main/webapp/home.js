angular.module('clinicalTrialApp', ['ui.router', 'ngAnimate'])

.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {

    $stateProvider
        .state('home', {
            url: '/home',
            templateUrl: 'home.html'
        })

        .state('searchBar', {
            url: '/searchBar',
            templateUrl: 'searchBar.html'
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
                $scope.numTrials = "";
                $http.get("http://localhost:8080/clin/clinical/getTrialsByCondition/" + $scope.condition).then(function(response) {
                    $scope.trials = response.data;
                    $scope.numTrials = response.data.size;
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
        });

    $urlRouterProvider.otherwise('/searchBar');

}]);
