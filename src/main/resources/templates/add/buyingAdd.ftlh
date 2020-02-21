<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Buying adding">
    <#if basketIds?has_content>
        <#if laptopModels?has_content>
            <h2>${errorMessage!}</h2>

            <h1>Додати нову покупку:</h1>
            <form method="post" action="/buying/add">
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
                                             value="${RequestParameters.totalPrice!}"/></label>
                <button type="submit">Додати</button>
            </form>
        <#else>
            <h1>Не знайдено жодного ноутбуку.</h1>
            <p><a href="/laptop/add">Додайте новий ноутбук</a> для створення покупки.</p>
        </#if>
    <#else>
        <h1>Не знайдено жодного кошику.</h1>
        <p><a href="/basket/add">Додайте новий кошик</a> для створення покупки.</p>
    </#if>
</@pad.page>