app.controller("loginController" ,function ($http, $location, $rootScope, $scope, authService) {

    $scope.login = function (credentials) {

        $http.post("/auth/login", {username: credentials.username, password: credentials.password}).then(
            function (response) {
                authService.setToken(response.data.accessToken);
                $rootScope.authentication = true;
                $location.url("/");
            })
            .catch(function(response){
                alert("Bad credentials");
            })
    }

})