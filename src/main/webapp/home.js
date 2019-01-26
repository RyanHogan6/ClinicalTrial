var app = angular.module('clinicalTrialApp', ['ui.router', 'ngMaterial', 'ngAnimate']);

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
		templateUrl: 'displayResults.html',
		controller: function($scope, $http, $stateParams) {
			$scope.condition = $stateParams.condition;
			$scope.facet = "Recruiting";
			$scope.facetType = "status";
			$http.get("http://localhost:8080/clin/clinical/getRecruitingTrialsByCondition/" + $scope.condition).then(function (response) {
				$scope.trials = response.data;
			});
		}

	})

	.state('allTrials', {
		url: '/fullTrialList?condition&facet',
		templateUrl: 'displayResults.html',
		controller: function ($scope, $http, $stateParams) {
			$scope.condition = $stateParams.condition;
			var query = "trials?hasCondition=" + $scope.condition;
			if($stateParams.facet != null){
				$scope.facet = $stateParams.facet;
				$scope.facetType = "intervention";
				query+="&hasInterventionName=" + $scope.facet;
			
			}
			else {
				$scope.facet = null;
				$scope.facetType = null;
			}

			$http.get("http://localhost:8080/clin/clinical/getTrialsByQuery/" + query).then(function(response) {
				console.log(response.data);
				$scope.trials = response.data;
			});
			if($stateParams.facet == null){
				$http.get("http://localhost:8080/clin/clinical/getFacetsByQuery/" + query).then(function(response) {
					console.log(response.data.results);
					$scope.facetsForTrials = response.data.results;
				});
			}
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

			var geocoder;
			var map;

			$scope.initMap = function (city, state) {

				console.log(city + "city" + " ___ " + state + "state");

				var address = city + "," + state;

				geocoder = new google.maps.Geocoder();
				var latlng = new google.maps.LatLng(-34.397, 150.644);
				var myOptions = {
						zoom: 8,
						center: latlng,
						mapTypeControl: true,
						mapTypeControlOptions: {style: google.maps.MapTypeControlStyle.DROPDOWN_MENU},
						navigationControl: true,
						mapTypeId: google.maps.MapTypeId.ROADMAP
				};

				map = new google.maps.Map(document.getElementById("map"), myOptions);

				if (geocoder) {
					geocoder.geocode( { 'address': address}, function(results, status) {
						if (status === google.maps.GeocoderStatus.OK) {
							if (status !== google.maps.GeocoderStatus.ZERO_RESULTS) {
								map.setCenter(results[0].geometry.location);

								var marker = new google.maps.Marker({
									position: results[0].geometry.location,
									map: map,
									title: address
								});
								google.maps.event.addListener(marker, 'click', function () {
									infowindow.open(map, marker);
								});

							}
						}
					});
				}
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
		templateUrl: 'map.html'
	})

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



	$urlRouterProvider.otherwise('/searchBar');
}]);


app.controller('autoCompleteController', function($scope, $http) {


	$scope.conditionList = [];
	var data = "";
	var temp = [];

	$http.get('http://localhost:8080/clin/clinical/getAllConditions').then(function(response) {
		data = response.data.toString();
		temp = data.toString().replace("[", "").replace("]", "").split(",");
		console.log(temp);
	});



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
