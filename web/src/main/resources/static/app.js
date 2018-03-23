var app = angular.module("funnyqrz", ["ngRoute"]);

app.config(function ($routeProvider, $httpProvider) {
    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

    $routeProvider
        .when("/", {
            templateUrl: "home/home.html"
        })
        .when("/login", {
            templateUrl: "./account/login.html",
            controller: "loginController"
        })
        .when("/reports", {
            templateUrl: "report/report.html",
            controller: "reportController"
        })
        .when("/register", {
            templateUrl: "register/register.html",
            controller: "registerController"
        })
        .when("/logout", {
            templateUrl: "account/logout.html"
        })
        .otherwise({ redirectTo: '/' });
})

app.controller("parrentController", function ($rootScope, authService) {

    $rootScope.logout = function () {
        $rootScope.authentication = false;
        authService.removeToken();

    }

})
