import Vue from 'vue';
import $ from 'jquery'
Vue.filter('convertDate', () => {
    return 'data';
})
Vue.directive('chunk', {
    inserted: function (el) {
        if($(el).text().length > 5){
            var tmp = document.createElement('a');
            el.classList.forEach(item => {
                tmp.classList.add(item);
            })
            tmp.innerText = $(el).text().substring(0,5)+'...';
            tmp.setAttribute('data-text', $(el).text());
            tmp.addEventListener('click', function () {
                var element = $(tmp)[0];
                element.innerHTML = $(tmp).attr('data-text');
                $(tmp).replaceWith(element);
            })
            $(el).replaceWith(tmp);
        }
    }
})