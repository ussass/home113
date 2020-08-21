Element.prototype.appendAfter = function (elemsd) {
    elemsd.parentNode.insertBefore(this, elemsd.nextSibling);
}, false;
var Ix = 0;
var Sx = 0;
var color;

function iAdd() {
    while (document.getElementById('ingredient' + Ix) == null) {
        Ix--;
    }
    var newElement = document.createElement('div');
    newElement.appendAfter(document.getElementById('ingredient' + Ix));
    Ix++;
    newElement.id = 'ingredient' + Ix;

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

function iDel(whom) {
    document.getElementById("formIng").removeChild(document.getElementById("ingredient" + whom));
}

function sAdd() {

    while (document.getElementById('step' + Sx) == null) {
        Sx--;
    }
    var newElement = document.createElement('div');
    newElement.appendAfter(document.getElementById('step' + Sx));
    Sx++;
    newElement.id = 'step' + Sx;

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

function sDel(whom) {
    document.getElementById("formStp").removeChild(document.getElementById("step" + whom));
}

function init() {
    var ing = document.getElementById("formIng");
    var lastIng = ing.getElementsByClassName("ingJS")[ing.getElementsByClassName("ingJS").length - 1].getAttribute("id");
    Ix = parseInt(lastIng.match(/\d+/));
    var stp = document.getElementById("formStp");
    var lastStp = stp.getElementsByClassName("stepJS")[stp.getElementsByClassName("stepJS").length - 1].getAttribute("id");
    Sx = parseInt(lastStp.match(/\d+/));
}

function changeFunc() {
    var selectedValue = selectBox.options[selectBox.selectedIndex].value;
    var lastId = document.getElementById("hideBlock").lastElementChild.id;
    var lastNumid = parseInt(lastId.match(/\d+/))
    for (var i = 0; i <= lastNumid; i++) {
        var element = document.getElementById("quan" + i);
        if (!element) continue;
        document.getElementById("quan" + i).textContent = Math.round(document.getElementById("quanOri" + i).textContent / document.getElementById("portOri").textContent * selectedValue);
    }
}

function recipeDelCheck() {
    var left = document.getElementById("leftButton");
    var right = document.getElementById("RightButton");

    if (right.getElementsByTagName("button")[0].innerHTML === "Внести изменения") {
        right.removeChild(right.getElementsByTagName("button")[0]);
        right.insertAdjacentHTML("beforeEnd", "<button type=\"submit\" class=\"w3-block w3-btn w3-deep-orange w3-round-large\">Удалить</button>");

        left.removeChild(left.getElementsByTagName("div")[0]);
        left.insertAdjacentHTML("beforeEnd", "<div class=\"w3-block w3-btn " + color + " w3-round-large\" onclick=\"recipeDelCheck()\">Отмена</div>");

        document.getElementsByTagName("form")[0].action = "/recipe/del";
    }
    else {
        right.removeChild(right.getElementsByTagName("button")[0]);
        right.insertAdjacentHTML("beforeEnd", "<button type=\"submit\" class=\"w3-block w3-btn " + color + " w3-round-large\">Внести изменения</button>");

        left.removeChild(left.getElementsByTagName("div")[0]);
        left.insertAdjacentHTML("beforeEnd", "<div class=\"w3-block w3-btn w3-deep-orange w3-round-large\" onclick=\"recipeDelCheck()\">Удалить рецепт?</div>");

        document.getElementsByTagName("form")[0].action = "/recipe/edit";
    }
}

function color() {
    color = document.getElementById("getColor").className.replace("w3-container w3-center ", "");
    // return document.getElementById("getColor").className.replace("w3-container w3-center ", "");
    // alert(color)

}

function chekingImg() {
    var img = document.getElementsByClassName("cuting");
    // img[0].tagName;
    alert(img[0].textContent);
}

function photoEdit(num, type) {
    var x;
    if (type === 1) x = document.getElementById("photoEditMain" + num);
    else x = document.getElementById("photoEdit" + num);

    if (x.getElementsByTagName("img")[0].className.indexOf("w3-opacity") == -1) {
        x.getElementsByTagName("img")[0].className += " w3-opacity";
        x.getElementsByTagName("div")[0].className = x.getElementsByTagName("div")[0].className.replace(" w3-hide", "");
        x.getElementsByTagName("input")[0].value = 1;
    } else {
        x.getElementsByTagName("img")[0].className = x.getElementsByTagName("img")[0].className.replace(" w3-opacity", "");
        x.getElementsByTagName("div")[0].className += " w3-hide"
        x.getElementsByTagName("input")[0].value = 0;
    }
}