<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Buying editing">
    <h2>${errorMessage!}</h2>

    <h1>Зміна інформації покупки:</h1>
    <form method="post" action="/buying/edit/${editBuying.id}">
        <#if basketIds?has_content>
            <label>ID кошику:
                <select name="basketId">
                    <#list basketIds as basketId>
                        <#if editBuying.basket??>
                            <option value="${basketId}"<#if basketId == editBuying.basket.id>
                                selected="selected"</#if>>${basketId}</option>
                        <#else>
                            <option>${basketId}</option>
                        </#if>
                    </#list>
                </select>
            </label>
        </#if>

        <#if laptopModels?has_content>
            <label>Модель ноутбуку:
                <select name="laptopModel">
                    <#list laptopModels as laptopModel>
                        <#if editBuying.laptop??>
                            <option value="${laptopModel}"<#if laptopModel == editBuying.laptop.label.model>
                                selected="selected"</#if>>${laptopModel}</option>
                        <#else>
                            <option>${laptopModel}</option>
                        </#if>
                    </#list>
                </select>
            </label>
        </#if>

        <label>Загальна сума: <input type="number" step="1" min="5000" name="totalPrice"
                                     value="${(editBuying.totalPrice??)?then(editBuying.totalPrice?c, '')}"/></label>
        <button type="submit">Зберегти</button>
    </form>
</@pad.page>