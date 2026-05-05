// =================================================
// 🔥 AngularJS Module
// =================================================
var registerApp = angular.module('registerApp', []);

// =================================================
// 🔥 Register Controller
// =================================================
registerApp.controller('RegisterController', function($scope, $http) {

    // =================================================
    // 1. 初始化資料
    // =================================================
    $scope.form = {};
    $scope.countries = [];

    $scope.msg = "";
    $scope.msgType = "";

    // =================================================
    // 2. 取得國籍資料（API）
    // =================================================
    $scope.getCountries = function() {

        $http.get("http://localhost:8080/api/student/countries")
            .then(function(response) {

                // 成功：塞進下拉選單
                $scope.countries = response.data;
                console.log("國籍載入成功", response.data);

            })
            .catch(function(error) {

                console.error("國籍 API 失敗", error);

                $scope.msg = "無法載入國籍資料";
                $scope.msgType = "danger";
            });
    };

    // =================================================
    // 3. 註冊功能
    // =================================================
    $scope.submitForm = function() {

        // ❗ 密碼驗證
        if ($scope.form.password !== $scope.form.confirmPassword) {
            $scope.msg = "密碼不一致";
            $scope.msgType = "danger";
            return;
        }

        // API 呼叫
        $http.post("http://localhost:8080/api/student/register", $scope.form)
            .then(function(response) {

                if (response.data.success) {
                    $scope.msg = response.data.message;
                    $scope.msgType = "success";

                    // 成功後清空表單
                    $scope.form = {};

                } else {
                    $scope.msg = response.data.message;
                    $scope.msgType = "danger";
                }

            })
            .catch(function(error) {

                console.error("註冊失敗", error);

                $scope.msg = "系統錯誤，請稍後再試";
                $scope.msgType = "danger";
            });
    };

    // =================================================
    // 4. 頁面載入自動執行
    // =================================================
    $scope.getCountries();

});