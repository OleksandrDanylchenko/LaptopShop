<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Buying editing">
    <h2>${errorMessage!}</h2>

    <h1>Зміна інформації покупки:</h1>
    <form method="post" action="/buying/edit/${editBuying.id}">
        <label>ID кошику:
            <select name="basketId">
                <#list basketIds as basketId>
                    <option>${basketId}</option>
                </#list>
            </select>
        </label>
        <label>Модель ноутбуку
            <select name="laptopModel">
                <#list laptopModels as laptopModel>
                    <option>${laptopModel}</option>
                </#list>
            </select>
        </label>
        <label>Загальна сума: <input type="number" step="10" min="5000" name="totalPrice"
                                     value="${(editBuying.totalPrice??)?then(editBuying.totalPrice?c, '')}"/></label>
        <button type="submit">Зберегти</button>
    </form>
</@pad.page>