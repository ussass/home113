Element.prototype.appendAfter = function (elemsd) {
    elemsd.parentNode.insertBefore(this, elemsd.nextSibling);
}, false;
var Ix = 0;
var Sx = 0;
var Px = 0;

function iAdd(){
    while(document.getElementById('ingredient'+Ix) == null){
        Ix--;
    }
    var newElement = document.createElement('div');
    newElement.appendAfter(document.getElementById('ingredient'+ Ix));
    Ix++;
    newElement.id = 'ingredient'+ Ix;

    var inLabel = document.getElementById('ingredient' + Ix);
    inLabel.insertAdjacentHTML("beforeEnd", "<br /><div class=\"w3-row\">\n" +
        "                            <div class=\"w3-half\">\n" +
        "                                <div style=\"padding: 8px 16px\">Ингредиент</div>\n" +
        "                            </div>\n" +
        "                            <div class=\"w3-half\">\n" +
        "                                <div onclick=\"iDel(" + Ix + ")\" class=\"w3-button w3-right w3-deep-orange w3-round-large\">Удалить</div>\n" +
        "                            </div>\n" +
        "                        </div>\n" +
        "                        <br />\n" +
        "                        <input type=\"text\" name=\"ingName\" class=\"w3-input w3-border w3-round-large\" placeholder=\"Название ингредиента\"><br />\n" +
        "                        <div class=\"w3-row\">\n" +
        "                            <div class=\"w3-half\">\n" +
        "                                <input type=\"number\" name=\"quantity\" placeholder=\"0\" class=\"w3-input w3-border w3-round-large\">\n" +
        "                            </div>\n" +
        "                            <div class=\"w3-half\">\n" +
        "                                <select name=\"measure\" class=\"w3-select w3-border w3-round-large\">\n" +
        "                                    <option value=\"0\">грамм</option>\n" +
        "                                    <option value=\"1\">килограмм</option>\n" +
        "                                    <option value=\"2\">миллилитр</option>\n" +
        "                                    <option value=\"3\">литр</option>\n" +
        "                                    <option value=\"4\">по вкусу</option>\n" +
        "                                    <option value=\"5\">на кончике ножа</option>\n" +
        "                                    <option value=\"6\">защепотка</option>\n" +
        "                                    <option value=\"7\">чайная ложка</option>\n" +
        "                                    <option value=\"8\">столовая ложка</option>\n" +
        "                                    <option value=\"9\">стакан</option>\n" +
        "                                    <option value=\"10\">головка</option>\n" +
        "                                    <option value=\"11\">штука</option>\n" +
        "                                    <option value=\"12\">кусок</option>\n" +
        "                                </select>\n" +
        "                            </div>\n" +
        "                        </div><br /><br />\n")
}

function iDel(whom){
    document.getElementById("formIng").removeChild(document.getElementById("ingredient" + whom));
}

function sAdd(){

    while(document.getElementById('step'+Sx) == null){
        Sx--;
    }
    var newElement = document.createElement('div');
    newElement.appendAfter(document.getElementById('step'+ Sx));
    Sx++;
    newElement.id = 'step'+ Sx;

    var inLabel = document.getElementById('step' + Sx);

    inLabel.insertAdjacentHTML("beforeEnd", "<br /><div class=\"w3-row\">\n" +
        "                            <div class=\"w3-half\">\n" +
        "                                <div style=\"padding: 8px 16px\">Далее</div>\n" +
        "                            </div>\n" +
        "                            <div class=\"w3-half\">\n" +
        "                                <div onclick=\"sDel(" + Sx + ")\" class=\"w3-button w3-right w3-deep-orange w3-round-large\">Удалить</div>\n" +
        "                            </div>\n" +
        "                        </div>\n" +
        "                        <div style=\"padding: 8px 16px\">\n" +
        "                            <label for=\"photoStep\">Добавить изображение к описанию</label>\n" +
        "                            <input type=\"file\" name=\"photoStep\" id=\"photoStep\" class=\"w3-center\" accept=\".jpg, .jpeg, .png, .gif\">\n" +
        "                        </div>\n" +
        "                        <br />\n" +
        "                        <textarea name=\"step\" class=\"w3-input w3-border w3-round-large\" style=\"resize: vertical\" placeholder=\"Описание\"></textarea><br /><br />");
}

function sDel(whom){
    document.getElementById("formStp").removeChild(document.getElementById("step" + whom));
}