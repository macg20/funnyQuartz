app.factory('reportService', function ($http, authService) {

    return {
        getReports: function () {

            return $http({
                headers: authService.createAuthorizationTokenHeader(),
                method: 'GET',
                url: 'http://localhost:8080/reports/'
            })
        },

        getReportContent: function (id) {
          return $http({
                headers: authService.createAuthorizationTokenHeader(),
                method: 'GET',
                url: 'http://localhost:8080/reports/content/' + id
            })
        }
    }

});