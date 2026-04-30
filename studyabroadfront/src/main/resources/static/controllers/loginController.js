app.controller('LoginController', function($scope, AuthService) {

    $scope.loginForm = {};
    $scope.message = "";

    $scope.login = function() {

        AuthService.login($scope.loginForm)
            .then(function(res) {
                $scope.message = res.data.message;
                console.log("login success", res.data);
            })
            .catch(function(err) {
                $scope.message = err.data.message || "login failed";
                console.log("login error", err);
            });

    };

});