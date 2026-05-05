// 建立一個 AngularJS 模組 (Module)，名稱必須與 HTML 中的 ng-app="nzeduApp" 一致
var courseapp = angular.module('nzeduApp', []);

// 建立 Controller，名稱必須與 HTML 中的 ng-controller="CourseController" 一致
// 這裡注入了 $scope (負責綁定 HTML 變數) 與 $http (負責呼叫後端 API)
courseapp.controller('CourseController', function($scope, $http) {
    
    // 1. 初始化變數
    $scope.courses = [];          
    $scope.selectedCountryId = 3; 

    // 2. 定義抓取課程資料的方法
    $scope.getCourses = function() {
        
        // 防呆機制：如果沒有選擇國家，就不發送請求並清空列表
        if (!$scope.selectedCountryId) {
            $scope.courses = [];
            return;
        }

        // 組合後端 API 的網址 (對應您的 CourseController)
        var apiUrl = 'http://localhost:8080/api/courses?countryId=' + $scope.selectedCountryId;

        // 使用 $http 發送 GET 請求
        $http.get(apiUrl)
            .then(function(response) {
                // 成功：將後端回傳的 JSON 資料 (List<Course>) 存入 $scope.courses
                // 這樣 HTML 裡面的 ng-repeat 就會自動更新畫面！
                $scope.courses = response.data;
                console.log("成功取得資料：", response.data);
            })
            .catch(function(error) {
                // 失敗：印出錯誤訊息，並跳出警告視窗
                console.error('API 呼叫失敗:', error);
                alert('無法連線到資料庫或後端，請確認 Spring Boot 伺服器是否正常運作！');
            });
    };

    // 3. 當網頁一載入時，自動執行一次抓取資料 (載入預設國家 1 的課程)
    $scope.getCourses();
	
	});