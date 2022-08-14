angular.module('app', []).controller('indexController', function ($scope, $http, $rootScope) {
    const contextPath = 'http://localhost:8189/app/api/v1/products';
    let currentPage = 1;
    let lastPage = 0;
    let filterOn = false;

    $scope.loadCatalog = function (page, min, max) {
        currentPage = page;
        $http({
            url: contextPath,
            method: 'GET',
            params: {
                page: page,
                min: min,
                max: max
            }
        }).then(function (response) {
            $scope.productList = response.data;
            lastPage = response.data.totalPages;
            var arr = [];
            for (var i = 0; i < lastPage; i++) {
                arr[i] = i + 1;
            }
            $scope.countPage = arr;
            $scope.getCartCount();
        });
    };

    $scope.deleteProduct = function (productId) {
        $http.delete(contextPath + '/' + productId)
            .then(function (response) {
                $scope.loadCatalog(lastPage);
            });
    };

    $scope.newProduct = function () {
        $http.post(contextPath, $scope.newProd)
            .then(function (response) {
                $scope.loadCatalog(lastPage);
            });
    };

    $scope.findBetween = function () {
        filterOn = true;
        $scope.loadCatalog($scope.currentPage, $scope.range.min, $scope.range.max);
    };

    $scope.clearFormMinMax = function () {
        filterOn = false;
        $scope.range.max = null;
        $scope.range.min = null;
        $scope.loadCatalog(1);
    };

    $scope.setCurrentPage = function (i) {
        currentPage = i;
        if (filterOn) {
            $scope.findBetween();
        } else {
            $scope.loadCatalog(currentPage);
        }
    };

    $scope.toCart = function (productId) {
        $http.post('http://localhost:8189/app/api/v1/cart/'+ productId, localStorage.cartName)
            .then(function (response) {
                $scope.loadCatalog(currentPage);
            });
    };

    $scope.getCartCount = function () {
        $http.post('http://localhost:8189/app/api/v1/cart/productCount/', localStorage.cartName)
            .then(function (response) {
                $scope.cartCount = response.data;
            });
    };

    $scope.tryToAuth = function () {
        $http.post('http://localhost:8189/app/api/v1/auth', $scope.user)
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
        localStorage.cartName ="cart_" + (Math.random() * 100);
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

    if(!localStorage.cartName){
        localStorage.cartName = "cart_" + (Math.random() * 100);
    }
    if (localStorage.springWebUser) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + localStorage.springToken;
        localStorage.cartName = "cart_" + localStorage.springWebUser;
    }
    $scope.loadCatalog(currentPage);
    $scope.getCartCount();
});