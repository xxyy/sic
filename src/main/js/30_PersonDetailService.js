tingoApp.factory('PersonDetailService', ['$http',
    function ($http) {
        var detailService = {};
        detailService.person = {name: 'Loading..'};
        detailService.quotes = [];
        var fetched = false;

        detailService.save = function (quote) {
            var myIndex = _.indexOf(detailService.quotes, quote);
            if (quote.text.length === 0 || !quote.text.trim()) {
                detailService.quotes.splice(myIndex);
                return;
            }

            $http.post('/api/quote/save', quote)
                .then(function (response) {
                    detailService.quotes[myIndex] = response.data;
                }, function (response) {
                    console.warn('Couldn\'t save fields: ');
                    console.warn(response);
                    alert('Fehler beim Speichern: ' + response.data.errorMessage);
                });
        };

        detailService.unedit = function (quote) {
            var myIndex = _.indexOf(detailService.quotes, quote);
            detailService.quotes[myIndex] = quote.backup;
        };

        detailService.edit = function (quote) {
            quote.backup = _.clone(quote);
            quote.editing = true;
        };

        detailService.new = function () {
            detailService.quotes.push({text: '', personId: detailService.person.id, editing: true, authorName: 'du'});
        };

        detailService.delete = function (quote) {
            $http.post('/api/quote/delete', quote)
                .then(function (response) {
                    detailService.quotes = _.without(detailService.quotes, quote);
                }, function (response) {
                    console.warn('Couldn\'t delete quote: ');
                    console.warn(response);
                    alert('Fehler beim Löschen: ' + response.data.errorMessage);
                });
        };

        detailService.fetch = function (id) {
            fetched = false;
            $http.get('/api/quote/by/person/' + id)
                .success(function (data) {
                    detailService.person = data.person;
                    detailService.quotes = data.quotes;
                    fetched = true;
                });
        };

        // Getters and Setters
        detailService.isFetched = function () {
            return fetched;
        };

        detailService.getQuotes = function () {
            return detailService.quotes;
        };

        detailService.getPerson = function () {
            return detailService.person;
        };

        return detailService;
    }]);