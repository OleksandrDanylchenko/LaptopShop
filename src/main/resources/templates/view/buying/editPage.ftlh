<#import "../../parts/htmlPad.ftlh" as htmlPad>

<@htmlPad.page "Buying editing">
    <div class="container">
        <p class="h1">Зміна інформації про покупку:</p>
        <form method="post" action="/buying/edit/${editBuying.id}" class="form-group needs-validation w-100" novalidate>
            <#if basketIds?has_content>
                <div class="form-row my-2">
                    <div class="col">
                        <label class="d-inline ml-2" for="basketId">ID кошику:</label>
                        <select class="custom-select" name="basketId" required>
                            <#list basketIds as basketId>
                                <#if editBuying.basket??>
                                    <option value="${basketId}"<#if basketId == editBuying.basket.id>
                                        selected="selected"</#if>>${basketId}</option>
                                <#else>
                                    <option>${basketId}</option>
                                </#if>
                            </#list>
                        </select>
                        <div class="invalid-feedback">Не обрано ID кошику!</div>
                    </div>
                </div>
            </#if>

            <#if laptopModels?has_content>
                <div class="form-row my-2">
                    <div class="col">
                        <label class="d-inline ml-2" for="laptopModel">Модель ноутбуку:</label>
                        <select class="custom-select" name="laptopModel" required>
                            <#list laptopModels as laptopModel>
                                <#if editBuying.laptop??>
                                    <option value="${laptopModel}"<#if laptopModel == editBuying.laptop.label.model>
                                        selected="selected"</#if>>${laptopModel}</option>
                                <#else>
                                    <option>${laptopModel}</option>
                                </#if>
                            </#list>
                        </select>
                        <div class="invalid-feedback">Не обрано модель ноутбуку!</div>
                    </div>
                </div>
            </#if>

            <div class="form-row my-2">
                <div class="col">
                    <label class="d-inline ml-2" for="totalPrice">Загальна ціна:</label>
                    <input type="number" min="5000" max="2147483646" class="form-control" name="totalPrice"
                           value="${(editBuying.totalPrice??)?then(editBuying.totalPrice?c, '')}" required/>
                    <div class="invalid-feedback">Уведено некоректну ціну! (<5000 aбо >2147483646)</div>
                </div>
            </div>

            <button type="submit" class="btn btn-danger float-right">Змінити</button>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        </form>
    </div>
</@htmlPad.page>