<#import "../../errorsHandlers/datesOrderBreaker.ftlh" as dO>
<#import "../../modalInputManage/inputsCleaner.ftlh" as fC>
<#import "../../modalInputManage/selectCleaner.ftlh" as sC>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro availabilityAdd>
    <div class="modal fade" id="availabilityAddModal" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="availabilityAddLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="availabilityAddLabel">Додати новий запис про наявність</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <#if shopAddresses?has_content>
                    <#if laptopModels?has_content>
                        <div class="modal-body">
                            <form method="post" id="availabilityAddForm" action="/availability/add"
                                  class="form-group needs-validation w-100" novalidate
                                  onsubmit="return validateDatesAdd()">
                                <div class="form-row mb-2">
                                    <div class="col">
                                        <label class="d-inline ml-2" for="laptopModel">Модель ноутбуку:</label>
                                        <select class="custom-select" id="laptopSelect" name="laptopModel" required>
                                            <option value="" selected disabled></option>
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
                                        <input type="number" id="priceInput" min="5000" max="2147483646"
                                               class="form-control" name="price" required/>
                                        <div class="invalid-feedback">Уведено некоректну ціну! (<5000)</div>
                                    </div>
                                    <div class="col">
                                        <label class="d-inline ml-2" for="laptopModel">Кількість:</label>
                                        <input type="number" id="quantityInput" min="1" max="2147483646"
                                               class="form-control" name="quantity" required/>
                                        <div class="invalid-feedback">Уведено некоректну кількість!</div>
                                    </div>
                                </div>
                                <div class="form-row my-2">
                                    <div class="col">
                                        <label class="d-inline ml-2" for="laptopModel">Адресу магазину:</label>
                                        <select class="custom-select" id="shopSelect" name="shopAddress" required>
                                            <option value="" selected disabled></option>
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
                                        <input type="date" id="newDateStart" class="form-control" name="dateStart"
                                               min="2019-02-16"
                                               required/>
                                        <div class="invalid-feedback">Уведено некоректну дату початку продаж!</div>
                                    </div>
                                    <div class="col">
                                        <label class="d-inline ml-2" for="dateEnd">Дата закінчення продаж:</label>
                                        <input type="date" id="newDateEnd" class="form-control" name="dateEnd"
                                               min="2019-02-16"
                                               required/>
                                        <div class="invalid-feedback">Уведено некоректну дату закінчення продаж!
                                        </div>
                                    </div>
                                    <@dO.datesBreaker id="Add" dateStartId="newDateStart" dateEndId="newDateEnd"/>
                                </div>
                                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            </form>
                            <@fC.inputsCleaner modalId="availabilityAddModal"
                            inputIds=["priceInput", "quantityInput", "newDateStart", "newDateEnd"]/>
                            <@sC.selectsCleaner modalId="availabilityAddModal"
                            selectIds=["laptopSelect", "shopSelect"]/>
                            <@fSc.fieldsStatusCleaner modalId="availabilityAddModal" formId="availabilityAddForm"/>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-dark" data-dismiss="modal">Скасувати</button>
                            <button type="submit" form="availabilityAddForm" class="btn btn-danger">Додати</button>
                        </div>
                    <#else>
                        <div class="modal-body">
                            <div class="alert alert-warning" role="alert">
                                <div class="h1 text-center">Не знайдено жодного ноутбука.</div>
                                <div class="h3 text-center"><a href="/laptop">Додайте новий ноутбук</a>
                                    для створення запису про наявність.
                                </div>
                            </div>
                        </div>
                    </#if>
                <#else>
                    <div class="modal-body">
                        <div class="alert alert-warning" role="alert">
                            <div class="h1 text-center">Не знайдено жодного активного магазину.</div>
                            <div class="h3 text-center"><a href="/shop">Додайте новий магазин</a>
                                для створення запису про наявність.
                            </div>
                        </div>
                    </div>
                </#if>
            </div>
        </div>
    </div>
</#macro>