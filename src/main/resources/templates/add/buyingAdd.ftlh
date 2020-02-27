<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/errorOutput.ftlh" as error>

<@pad.page "Buying adding">
    <#if basketIds?has_content>
        <#if laptopModels?has_content>
            <@error.errorOutput errorMessage!/>

            <div>
                <div class="h1">Додати нову покупку:</div>
                <form method="post" action="/buying/add" class="form-group needs-validation w-100" novalidate>
                    <div class="form-row my-2">
                        <div class="col">
                            <select class="custom-select" name="basketId">
                                <option value="" selected>Оберіть ID кошику</option>
                                <#list basketIds as basketId>
                                    <option>${basketId}</option>
                                </#list>
                            </select>
                        </div>
                    </div>
                    <div class="form-row my-2">
                        <div class="col">
                            <select class="custom-select" name="laptopModel">
                                <option value="" selected>Оберіть модель ноутбуку</option>
                                <#list laptopModels as laptopModel>
                                    <option>${laptopModel}</option>
                                </#list>
                            </select>
                        </div>
                    </div>
                    <div class="form-row my-2">
                        <div class="col">
                            <input type="number" step="1" min="5000" class="form-control" name="totalPrice"
                                   value="${RequestParameters.totalPrice!}" placeholder="Загальна ціна" required/>
                            <div class="invalid-feedback">Уведено некоректну ціну!</div>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-danger mt-1 float-right">Додати</button>
                </form>
            </div>
        <#else>
            <div class="alert alert-warning" role="alert">
                <div class="h1 text-center">Не знайдено жодного ноутбуку.</div>
                <div class="h3 text-center"><a href="/laptop/add">Додайте новий ноутбук</a> для створення покупки.</div>
            </div>
        </#if>
    <#else>
        <div class="alert alert-warning" role="alert">
            <div class="h1 text-center">Не знайдено жодного кошику.</div>
            <div class="h3 text-center"><a href="/basket/add">Додайте новий кошик</a> для створення покупки.</div>
        </div>
    </#if>
</@pad.page>