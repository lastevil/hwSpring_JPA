(function () {
    angular
        .module("store-front", ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
            .when('/store', {
                templateUrl: 'store/store.html',
                controller: 'storeController'
            })
            .when('/cart', {
                templateUrl: 'cart/cart.html',
                controller: 'cartController'
            })
            .when('/order', {
                templateUrl: 'order/order.html',
                controller: 'orderController'
            })
            .otherwise({
                redirectTo: '/store'
            });
    }

    function run($rootScope, $http, $localStorage) {
        if ($localStorage.springToken) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springToken;
        }
    }
})();

angular.module("store-front").controller("indexController", function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:5555/auth/api/v1';

    $scope.getAddresses = function () {
        $http.get('http://localhost:5555/orders/api/v1/order/addresses')
            .then(function (response) {
                $scope.addressList = response.data;
            });
    };

    $scope.tryToAuth = function () {
        $http.post(contextPath + '/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.springWebUser = $scope.user.username;
                    $localStorage.springToken =  response.data.token;
                    $localStorage.cartName = "cart_" + $scope.user.username;
                    $scope.user.username = null;
                    $scope.user.password = null;
                    $scope.getAddresses();
                }
                $scope.getCartCount();
            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
        if ($scope.user?.username) {
            $scope.user.username = null;
        }
        if ($scope.user?.password) {
            $scope.user.password = null;
        }
    };

    $scope.clearUser = function () {
        delete $localStorage.springWebUser;
        delete $localStorage.springToken;
        delete $scope.address;
        $http.defaults.headers.common.Authorization = '';
        $localStorage.cartName = "cart_" + (Math.random() * 100);
        $scope.getCartCount();
    };

    $rootScope.isUserLoggedIn = function () {
        if ($localStorage.springWebUser) {
            return true;
        } else {
            return false;
        }
    };
    $scope.getCartCount = function () {
        $http({
            url: 'http://localhost:5555/cart/api/v1/cart/productCount/',
            method: 'POST',
            data: {cartName: $localStorage.cartName}
        }).then(function (response) {
                $scope.cartCount = response.data;
            });
    };
    $scope.getCartCount();
    if (!$localStorage.cartName) {
        $localStorage.cartName = "cart_" + (Math.random() * 100);
    }
    if ($localStorage.springWebUser) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springToken;
        $localStorage.cartName = "cart_" + $localStorage.springWebUser;
    }
});