// 1. 建立「唯一」的 AngularJS 模組 (名稱必須與 HTML 中的 ng-app="nzeduApp" 一致)
// 注意：整個專案只要宣告一次帶有 [] 的 module 就好！
var app = angular.module('nzeduApp', []);

// 2. 系統全域設定：讓這個 App 發送的所有 API 請求，都自動帶上 Cookie (Session)
app.config(function($httpProvider) {
    // ⭐ 這行非常重要，跨域登入、權限驗證全靠它！
    $httpProvider.defaults.withCredentials = true;
});

// 3. 建立 CourseController
app.controller('CourseController', function($scope, $http) {
    
	// ⭐ 加這行：從 localStorage 取回登入狀態
	$scope.user = JSON.parse(localStorage.getItem('user'));
	

	// 登出
	$scope.logout = function() {
	    localStorage.removeItem('user');
		//$scope.user = null;
	    window.location.href = "index.html";
	};
	
    // 初始化變數
    $scope.courses = [];          
    $scope.selectedCountryId = ""; // 預設留空，讓它一開始顯示全部
    $scope.searchText = "";

    // 定義抓取課程資料的方法
    $scope.getCourses = function() {
        
        // 🌟 注意：這裡我們改呼叫 /api/courses/list (抓取全部上架課程)
        // 因為我們之前在後端是寫這個 API，然後用前端來做篩選，這樣速度最快！
        $http.get('http://localhost:8080/api/courses/list')
            .then(function(response) {
                var allCourses = response.data;

                // 前端篩選邏輯：檢查是否有選擇國家
                if ($scope.selectedCountryId) {
                    $scope.courses = allCourses.filter(function(c) {
                        // 只顯示符合國家，且狀態為「上架中(isActive: true)」的課程
                        return c.countryId == $scope.selectedCountryId && c.isActive === true;
                    });
                } else {
                    // 沒選國家，就顯示全部 (但依然過濾掉下架的課程)
                    $scope.courses = allCourses.filter(function(c) {
                        return c.isActive === true;
                    });
                }
                
                console.log("成功取得並過濾資料：", $scope.courses);
            })
            .catch(function(error) {
                console.error('API 呼叫失敗:', error);
                alert('無法連線到資料庫或後端，請確認 Spring Boot 伺服器是否正常運作！');
            });
    };

    // 當網頁一載入時，自動執行一次抓取資料
    $scope.getCourses();
});