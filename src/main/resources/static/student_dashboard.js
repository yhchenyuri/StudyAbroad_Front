var app = angular.module("studyApp", []);

app.controller("StudentController", function ($scope, $http) {

    // ======================
    // 初始化
    // ======================
    $scope.student = {};
    $scope.courseOrders = [];
    $scope.appointments = [];

    $scope.loading = true;
    $scope.error = "";

    // ======================
    // 取得 email
    // ======================
    function getEmail() {

        const user = JSON.parse(localStorage.getItem("user"));
        const email = user.email;

        console.log(email);

        return email;
    }

    // ======================
    // 載入 dashboard
    // ======================
    $scope.loadDashboard = function () {

        let email = getEmail();

        if (!email) {
            $scope.error = "請先登入";
            $scope.loading = false;
            return;
        }

        $http.get("/api/student/profile/" + email)
            .then(function (res) {

                let data = res.data;

                if (!data.success) {
                    $scope.error = data.message;
                    return;
                }

                // ======================
                // 對應 Spring Boot JSON
                // ======================
                $scope.student = data.student;
                $scope.courseOrders = data.courseOrders || [];
                $scope.appointments = data.appointments || [];

            })
            .catch(function (err) {
                console.error(err);
                $scope.error = "API 連線失敗";
            })
            .finally(function () {
                $scope.loading = false;
            });
    };

    // ======================
    // 狀態顏色
    // ======================
    $scope.getStatusClass = function (status) {

        if (!status) return "bg-secondary text-white px-3";

        switch (status) {

            case "PAID":
                return "bg-success text-white px-3";

            case "PENDING":
                return "bg-warning text-dark px-3";

            case "CANCEL":
                return "bg-danger text-white px-3";

            default:
                return "bg-secondary text-white px-3";
        }
    };

    // ======================
    // 初始化
    // ======================
    $scope.loadDashboard();

});