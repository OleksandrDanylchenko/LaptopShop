<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/errorOutput.ftlh" as error>

<@pad.page "Basket adding">
    <#if clientIds?has_content>
        <#if employeeIds?has_content>
            <@error.errorOutput errorMessage!/>

            <div>
                <div class="h1">Додати новий кошик:</div>
                <form method="post" action="/basket/add" class="form-group needs-validation w-100" novalidate>
                    <div class="form-row mt-4">
                        <div class="col">
                            <select class="custom-select" name="employeeId">
                                <option value="" selected>Оберіть ID співробітника</option>
                                <#list employeeIds as employeeId>
                                    <option>${employeeId}</option>
                                </#list>
                            </select>
                        </div>
                    </div>
                    <div class="form-row my-2">
                        <div class="col">
                            <select class="custom-select" name="clientId">
                                <option value="" selected>Оберіть ID покупця</option>
                                <#list clientIds as clientId>
                                    <option>${clientId}</option>
                                </#list>
                            </select>
                        </div>
                    </div>
                    <div class="form-row my-2">
                        <div class="col">
                            <label for="dateTimeStr">Дата/час покупки:</label>
                            <input type="datetime-local" min="2020-02-16T00:00" class="form-control"
                                   name="dateTimeStr" value="${RequestParameters.dateTimeStr!}"
                                   placeholder="Дата/Час покупки" required/>
                            <div class="invalid-feedback">Некоректна дата/час покупки!</div>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-danger mt-1 float-right">Додати</button>
                </form>
            </div>
        <#else>
            <div class="alert alert-warning" role="alert">
                <div class="h1 text-center">Не знайдено жодного співробітника.</div>
                <div class="h3 text-center"><a href="/employee/add">Додайте нового співробітника</a> для створення
                    кошика.
                </div>
            </div>
        </#if>
    <#else>
        <div class="alert alert-warning" role="alert">
            <div class="h1 text-center">Не знайдено жодного активного магазину.</div>
            <div class="h3 text-center"><a href="/shop/add">Додайте новий магазин</a> для створення кошика.</div>
        </div>
    </#if>
</@pad.page>