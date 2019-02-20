/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { IngredientAmountComponentsPage, IngredientAmountDeleteDialog, IngredientAmountUpdatePage } from './ingredient-amount.page-object';

const expect = chai.expect;

describe('IngredientAmount e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let ingredientAmountUpdatePage: IngredientAmountUpdatePage;
    let ingredientAmountComponentsPage: IngredientAmountComponentsPage;
    let ingredientAmountDeleteDialog: IngredientAmountDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load IngredientAmounts', async () => {
        await navBarPage.goToEntity('ingredient-amount');
        ingredientAmountComponentsPage = new IngredientAmountComponentsPage();
        await browser.wait(ec.visibilityOf(ingredientAmountComponentsPage.title), 5000);
        expect(await ingredientAmountComponentsPage.getTitle()).to.eq('Ingredient Amounts');
    });

    it('should load create IngredientAmount page', async () => {
        await ingredientAmountComponentsPage.clickOnCreateButton();
        ingredientAmountUpdatePage = new IngredientAmountUpdatePage();
        expect(await ingredientAmountUpdatePage.getPageTitle()).to.eq('Create or edit a Ingredient Amount');
        await ingredientAmountUpdatePage.cancel();
    });

    it('should create and save IngredientAmounts', async () => {
        const nbButtonsBeforeCreate = await ingredientAmountComponentsPage.countDeleteButtons();

        await ingredientAmountComponentsPage.clickOnCreateButton();
        await promise.all([
            ingredientAmountUpdatePage.setIngredientAmountInput('5'),
            ingredientAmountUpdatePage.setIngredientUnitInput('ingredientUnit'),
            ingredientAmountUpdatePage.recipeeSelectLastOption(),
            ingredientAmountUpdatePage.ingredientSelectLastOption()
        ]);
        expect(await ingredientAmountUpdatePage.getIngredientAmountInput()).to.eq('5');
        expect(await ingredientAmountUpdatePage.getIngredientUnitInput()).to.eq('ingredientUnit');
        await ingredientAmountUpdatePage.save();
        expect(await ingredientAmountUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await ingredientAmountComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last IngredientAmount', async () => {
        const nbButtonsBeforeDelete = await ingredientAmountComponentsPage.countDeleteButtons();
        await ingredientAmountComponentsPage.clickOnLastDeleteButton();

        ingredientAmountDeleteDialog = new IngredientAmountDeleteDialog();
        expect(await ingredientAmountDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Ingredient Amount?');
        await ingredientAmountDeleteDialog.clickOnConfirmButton();

        expect(await ingredientAmountComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
