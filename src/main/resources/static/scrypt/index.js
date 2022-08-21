(function () {
    angular
        .module('store-front', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
            .when('/store', {
                templateUrl: 'html/store.html',
                controller: 'storeController'
            })
            .when('/cart', {
                templateUrl: 'html/cart.html',
                controller: 'cartController'
            })
            .when('/order', {
                templateUrl: 'html/order.html',
                controller: 'orderController'
            })
            .otherwise({
                redirectTo: '/'
            });
    }

    function run($rootScope, $http, localStorage) {
        if (localStorage.springWebUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + localStorage.springWebUser.token;
        }
    }
})();


angular.module('store-front').controller('indexController', function ($scope, $rootScope, $http) {
  const contextPath = 'http://localhost:8189/app/api/v1';

    $scope.tryToAuth = function () {
        $http.post(contextPath + '/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    localStorage.springWebUser = $scope.user.username;
                    localStorage.springToken = response.data.token;
                    $scope.user.username = null;
                    $scope.user.password = null;
                }
                $scope.loadCatalog(currentPage);
                $scope.getCartCount();
            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
        if ($scope.user.username) {
            $scope.user.username = null;
        }
        if ($scope.user.password) {
            $scope.user.password = null;
        }
    };

    $scope.clearUser = function () {
        delete localStorage.springWebUser;
        $http.defaults.headers.common.Authorization = '';
        localStorage.cartName = "cart_" + (Math.random() * 100);
        $scope.loadCatalog(currentPage);
        $scope.getCartCount();
    };

    $rootScope.isUserLoggedIn = function () {
        if (localStorage.springWebUser) {
            return true;
        } else {
            return false;
        }
    };

    if (!localStorage.cartName) {
        localStorage.cartName = "cart_" + (Math.random() * 100);
    }
    if (localStorage.springWebUser) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + localStorage.springToken;
        localStorage.cartName = "cart_" + localStorage.springWebUser;
    }
});