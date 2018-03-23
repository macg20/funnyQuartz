app.controller("reportController", function ($scope, $rootScope, $http, reportService, $location, authService) {

    if ($rootScope.authentication) {

        reportService.getReports().then(function (response) {
                $scope.reports = response.data;
            },
            function (response) {
            });
    }
    else {
        $location.url("/login");
    }

    $scope.downloadReport = function (id) {
        reportService.getReportContent(id).then(
            function (response) {
                var hrefData = "data:application/pdf;base64," + response.data.content;
                var a = document.getElementById("report-" + id);
                a.download = response.data.name;
                a.href = hrefData;
                a.click();

            }
        ).catch(function (reason) {
            alert(reason.status)
        })

    }
});