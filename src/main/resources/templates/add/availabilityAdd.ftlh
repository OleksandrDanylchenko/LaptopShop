<#import "../parts/htmlPad.ftlh" as pad>
<#import "../errorsHandlers/errorOutput.ftlh" as error>

<@pad.page "Availability adding">
    <#if shopAddresses?has_content>
        <#if laptopModels?has_content>
            <@error.errorOutput errorMessage!/>

            <div>
                <div class="h1">Додати запис про наявність:</div>
                <form method="post" action="/availability/add" class="form-group needs-validation w-100" novalidate>
                    <div class="form-row my-2">
                        <div class="col">
                            <label class="d-inline ml-2" for="laptopModel">Модель ноутбуку:</label>
                            <select class="custom-select" name="laptopModel" required>
                                <#list laptopModels as model>
                                    <option>${model}</option>
                                </#list>
                            </select>
                            <div class="invalid-feedback">Не обрано ноутбук!</div>
                        </div>
                    </div>
                    <div class="form-row my-2">
                        <div class="col">
                            <label class="d-inline ml-2" for="laptopModel">Ціна:</label>
                            <input type="number" min="5000" class="form-control" name="price"
                                   value="${RequestParameters.price!}" required/>
                            <div class="invalid-feedback">Уведено некоректну ціну!</div>
                        </div>
                        <div class="col">
                            <label class="d-inline ml-2" for="laptopModel">Кількість:</label>
                            <input type="number" min="1" class="form-control" name="quantity"
                                   value="${RequestParameters.quantity!}" required/>
                            <div class="invalid-feedback">Уведено некоректну кількість!</div>
                        </div>
                    </div>
                    <div class="form-row my-2">
                        <div class="col">
                            <label class="d-inline ml-2" for="laptopModel">Адресу магазину:</label>
                            <select class="custom-select" name="shopAddress" required>
                                <#list shopAddresses as address>
                                    <option>${address}</option>
                                </#list>
                            </select>
                            <div class="invalid-feedback">Не обрано адресу магазину!</div>
                        </div>
                    </div>
                    <div class="form-row my-2">
                        <div class="col">
                            <label class="d-inline ml-2" for="dateStart">Дата початку продаж:</label>
                            <input type="date" class="form-control" name="dateStart" min="2019-02-16"
                                   value="${RequestParameters.dateStart!}" required/>
                            <div class="invalid-feedback">Уведено некоректну дату початку продаж!</div>
                        </div>
                        <div class="col">
                            <label class="d-inline ml-2" for="dateEnd">Дата закінчення продаж:</label>
                            <input type="date" class="form-control" name="dateEnd" min="2019-02-16"
                                   value="${RequestParameters.dateEnd!}" required/>
                            <div class="invalid-feedback">Уведено некоректну дату закінчення продаж!</div>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-danger mt-1 float-right">Додати</button>
                </form>
            </div>
        <#else>
            <div class="alert alert-warning" role="alert">
                <div class="h1 text-center">Не знайдено жодного ноутбука.</div>
                <div class="h3 text-center"><a href="/laptop/add">Додайте новий ноутбук</a>
                    для створення запису про наявність.</div>
            </div>
        </#if>
    <#else>
        <div class="alert alert-warning" role="alert">
            <div class="h1 text-center">Не знайдено жодного активного магазину.</div>
            <div class="h3 text-center"><a href="/shop/add">Додайте новий магазин</a>
                для створення запису про наявність.</div>
        </div>
    </#if>
</@pad.page>