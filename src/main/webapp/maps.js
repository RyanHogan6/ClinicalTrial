
app.controller('mapCtrl', function($scope) {
    $scope.getAddress = function(country, city, state, zip) {
        console.log(country + city + state+ zip);
    };


});
