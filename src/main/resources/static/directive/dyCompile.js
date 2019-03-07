MyApp.directive("dyCompile", ["$compile", function($compile) {
    return {
        replace: true,
        restrict: 'EA',
        link: function(scope, elm, iAttrs) {
            var DUMMY_SCOPE = {
                    $destroy: angular.noop
                },
                root = elm,
                childScope,
                destroyChildScope = function() {
                    (childScope || DUMMY_SCOPE).$destroy();
                };

            iAttrs.$observe("html", function(html) {
                if (html) {
                    destroyChildScope();
                    childScope = scope.$new(false);
                    var content = $compile(html)(childScope);
                    root.replaceWith(content);
                    root = content;
                }

                scope.$on("$destroy", destroyChildScope);
            });
        }
    };
}])
