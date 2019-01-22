MyApp
    .service('commonService',['$http', '$q',function ($http, $q) {
        return {
             sliceArr :function(array, size) {
            var result = [];
            for (var x = 0; x < Math.ceil(array.length / size); x++) {
                var start = x * size;
                var end = start + size;
                result.push(array.slice(start, end));
            }
            return result;
        }


        };}]);

