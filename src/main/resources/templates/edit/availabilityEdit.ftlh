<#import "../parts/htmlPad.ftlh" as pad>
<#import "../errorsHandlers/errorOutput.ftlh" as error>

<@pad.page "Availability editing">
    <@error.errorOutput errorMessage!/>

    <div class="h1">Змінити запис про наявність:</div>
    <form method="post" action="/availability/edit/${editAvailability.id}" class="form-group needs-validation w-100"
          novalidate>
        <#if laptopModels?has_content>
            <div class="form-row my-2">
                <div class="col">
                    <label class="d-inline ml-2" for="laptopModel">Модель ноутбуку:</label>
                    <select class="custom-select" name="laptopModel" required>
                        <#list laptopModels as model>
                            <#if editAvailability.laptop??>
                                <option value="${model}"<#if model == editAvailability.laptop.label.model>
                                    selected="selected"</#if>>${model}</option>
                            <#else>
                                <option>${model}</option>
                            </#if>
                        </#list>
                    </select>
                    <div class="invalid-feedback">Не обрано ноутбук!</div>
                </div>
            </div>
        </#if>

        <div class="form-row my-2">
            <div class="col">
                <label class="d-inline ml-2" for="laptopModel">Ціна:</label>
                <input type="number" min="5000" class="form-control" name="price"
                       value="${(editAvailability.price??)?then(editAvailability.price?c, '')}" required/>
                <div class="invalid-feedback">Уведено некоректну ціну!</div>
            </div>
            <div class="col">
                <label class="d-inline ml-2" for="laptopModel">Кількість:</label>
                <input type="number" min="1" class="form-control" name="quantity"
                       value="${(editAvailability.quantity??)?then(editAvailability.quantity?c, '')}" required/>
                <div class="invalid-feedback">Уведено некоректну кількість!</div>
            </div>
        </div>

        <#if shopAddresses?has_content>
            <div class="form-row my-2">
                <div class="col">
                    <label class="d-inline ml-2" for="laptopModel">Адресу магазину:</label>
                    <select class="custom-select" name="shopAddress" required>
                        <#list shopAddresses as address>
                            <#if editAvailability.shop??>
                                <option value="${address}"<#if address == editAvailability.shop.address>
                                    selected="selected"</#if>>${address}</option>
                            <#else>
                                <option>${address}</option>
                            </#if>
                        </#list>
                    </select>
                    <div class="invalid-feedback">Не обрано адресу магазину!</div>
                </div>
            </div>
        </#if>

        <div class="form-row my-2">
            <div class="col">
                <label class="d-inline ml-2" for="dateStart">Дата початку продаж:</label>
                <input type="date" class="form-control" name="dateStart" min="2019-02-16"
                       value="${editAvailability.dateStart?iso_utc}" required/>
                <div class="invalid-feedback">Уведено некоректну дату початку продаж!</div>
            </div>
            <div class="col">
                <label class="d-inline ml-2" for="dateEnd">Дата закінчення продаж:</label>
                <input type="date" class="form-control" name="dateEnd" min="2019-02-16"
                       value="${editAvailability.dateEnd?iso_utc}" required/>
                <div class="invalid-feedback">Уведено некоректну дату закінчення продаж!</div>
            </div>
        </div>

        <button type="submit" class="btn btn-danger float-right">Зберегти</button>
    </form>
</@pad.page>