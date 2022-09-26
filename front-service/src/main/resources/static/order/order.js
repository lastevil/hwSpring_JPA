angular.module('store-front').controller('orderController', function ($scope, $http) {
    const contextPath = 'http://localhost:5555/orders/api/v1/order';
    $scope.getOrders = function () {
        $http.get(contextPath)
            .then(function (response) {
                $scope.ordersList = response.data;
            });
    };

    $scope.deleteOrder = function (id) {
        $http.delete(contextPath + '/' + id)
            .then(function (response) {
                $scope.getOrders();
            });
    };

    $scope.payOrder = function (id) {
        $http.get('http://localhost:5555/orders/api/v1/qiwi/create/' + id)
            .then(function (response) {
                $scope.billId = response.data.billId;
                params = {
                    payUrl: response.data.responseUrl
                }
                QiwiCheckout.openInvoice(params)
                    .then(function (onFullField) {
                        $http({
                            url: 'http://localhost:5555/orders/api/v1/qiwi/capture/',
                            method: 'POST',
                            data: {
                                orderId: id,
                                billId: $scope.billId
                            }
                        })
                    })
                    .then(function (onRejection) {
                        console.info('что-то пошло не так....');
                        $scope.getOrders();
                    });

            });
    };

    $scope.getOrderItems = function (id) {
        $http.get(contextPath + '/' + id)
            .then(function (response) {
                $scope.itemsList = response.data;
            });
    };

    $scope.getOrders();
});