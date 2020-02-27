<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/errorOutput.ftlh" as error>

<@pad.page "Buying editing">
    <@error.errorOutput errorMessage!/>

    <p class="h1">Зміна інформації про покупку:</p>
    <form method="post" action="/buying/edit/${editBuying.id}" class="form-group needs-validation w-100" novalidate>
        <#if basketIds?has_content>
            <div class="form-row my-2">
                <div class="col">
                    <select class="custom-select" name="basketId">
                        <option value="" selected>Оберіть ID кошику</option>
                        <#list basketIds as basketId>
                            <#if editBuying.basket??>
                                <option value="${basketId}"<#if basketId == editBuying.basket.id>
                                    selected="selected"</#if>>${basketId}</option>
                            <#else>
                                <option>${basketId}</option>
                            </#if>
                        </#list>
                    </select>
                </div>
            </div>
        </#if>

        <#if laptopModels?has_content>
            <div class="form-row my-2">
                <div class="col">
                    <select class="custom-select" name="laptopModel">
                        <option value="" selected>Оберіть модель ноутбуку</option>
                        <#list laptopModels as laptopModel>
                            <#if editBuying.laptop??>
                                <option value="${laptopModel}"<#if laptopModel == editBuying.laptop.label.model>
                                    selected="selected"</#if>>${laptopModel}</option>
                            <#else>
                                <option>${laptopModel}</option>
                            </#if>
                        </#list>
                    </select>
                </div>
            </div>
        </#if>

        <div class="form-row my-2">
            <div class="col">
                <input type="number" step="1" min="5000" class="form-control" name="totalPrice"
                       value="${(editBuying.totalPrice??)?then(editBuying.totalPrice?c, '')}" placeholder="Загальна ціна"/>
            </div>
        </div>

        <button type="submit" class="btn btn-danger mt-1 float-right">Зберегти</button>
    </form>
</@pad.page>