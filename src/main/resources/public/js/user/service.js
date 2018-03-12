'use strict';

App.factory('UserService', ['$http', '$q', '$document', function ($http, $q, $document) {

        self.csrfHeaderName = $document[0].querySelector("meta[name='_csrf_header']").getAttribute('content');
        self.csrf = $document[0].querySelector("meta[name='_csrf']").getAttribute('content');
        self.headers = {};
        self.headers[self.csrfHeaderName] = self.csrf;
        self.headers["Content-Type"] = 'application/json';

        return {
            loginUser: function (user) {
                return $http.post('/dvd-exchanger/user/login',
                       JSON.stringify(user), {headers: self.headers})
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while user logon');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            fetchUser: function () {
                            return $http.get('/dvd-exchanger/user/fetch')
                                    .then(
                                            function (response) {
                                                return response.data;
                                            },
                                            function (errResponse) {
                                                console.error('Error while fetching current user');
                                                return $q.reject(errResponse);
                                            }
                                    );
                        },
        };

    }]);