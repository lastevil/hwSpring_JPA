angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/app/api/v1';
    let currentPage = 1;
    let lastPage = 0;
    let filterOn = false;

    $scope.loadCatalog = function (page, min, max) {
        currentPage = page;
        $http({
            url: contextPath + "/products",
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
        $http.delete(contextPath + '/products/' + productId)
            .then(function (response) {
                $scope.loadCatalog(lastPage);
            });
    };

    $scope.newProduct = function () {
        $http.post(contextPath + '/products/', $scope.newProd)
            .then(function (response) {
                $scope.loadCatalog(lastPage);
            });
    }

    $scope.findBetween = function () {
        filterOn = true;
        $scope.loadCatalog($scope.currentPage, $scope.range.min, $scope.range.max);
    };

    $scope.clearFormMinMax = function () {
        filterOn = false;
        $scope.range.max = null;
        $scope.range.min = null;
        $scope.loadCatalog(1);
    }

    $scope.setCurrentPage = function (i) {
        currentPage = i;
        if (filterOn) {
            $scope.findBetween();
        } else {
            $scope.loadCatalog(currentPage);
        }
    };

    $scope.toCart = function (productId) {
        $http.post(contextPath + '/cart/'+ productId)
            .then(function (response) {
                $scope.loadCatalog(currentPage);
            })
    };

    $scope.getCartCount = function () {
        $http.get(contextPath + '/cart/count')
            .then(function (response) {
                $scope.cartCount = response.data;
            });
    }
    
    $scope.tryToAuth = function () {
        $http.post('http://localhost:8189/app/api/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.springWebUser = {username: $scope.user.username, token: response.data.token};

                    $scope.user.username = null;
                    $scope.user.password = null;
                }
            }, function errorCallback(response) {

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
        delete $localStorage.springWebUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $rootScope.isUserLoggedIn = function () {
        if ($localStorage.springWebUser) {
            return true;
        } else {
            return false;
        }
    };

    $scope.loadCatalog(currentPage);

});