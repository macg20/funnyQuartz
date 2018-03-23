app.controller("registerController", function ($scope, $rootScope) {
    $scope.templateUrl = "register/registerStart.html";


    $scope.register = function () {
        $scope.templateUrl = "register/registerSuccess.html"
    }

});
