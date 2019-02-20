/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RecipeeComponentsPage, RecipeeDeleteDialog, RecipeeUpdatePage } from './recipee.page-object';

const expect = chai.expect;

describe('Recipee e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let recipeeUpdatePage: RecipeeUpdatePage;
    let recipeeComponentsPage: RecipeeComponentsPage;
    let recipeeDeleteDialog: RecipeeDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Recipees', async () => {
        await navBarPage.goToEntity('recipee');
        recipeeComponentsPage = new RecipeeComponentsPage();
        await browser.wait(ec.visibilityOf(recipeeComponentsPage.title), 5000);
        expect(await recipeeComponentsPage.getTitle()).to.eq('Recipees');
    });

    it('should load create Recipee page', async () => {
        await recipeeComponentsPage.clickOnCreateButton();
        recipeeUpdatePage = new RecipeeUpdatePage();
        expect(await recipeeUpdatePage.getPageTitle()).to.eq('Create or edit a Recipee');
        await recipeeUpdatePage.cancel();
    });

    it('should create and save Recipees', async () => {
        const nbButtonsBeforeCreate = await recipeeComponentsPage.countDeleteButtons();

        await recipeeComponentsPage.clickOnCreateButton();
        await promise.all([
            recipeeUpdatePage.setNameInput('name'),
            recipeeUpdatePage.setDescriptionInput('description'),
            recipeeUpdatePage.setStepsInput('steps'),
            // recipeeUpdatePage.ingredientSelectLastOption(),
            recipeeUpdatePage.mealTypeSelectLastOption()
        ]);
        expect(await recipeeUpdatePage.getNameInput()).to.eq('name');
        expect(await recipeeUpdatePage.getDescriptionInput()).to.eq('description');
        expect(await recipeeUpdatePage.getStepsInput()).to.eq('steps');
        await recipeeUpdatePage.save();
        expect(await recipeeUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await recipeeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Recipee', async () => {
        const nbButtonsBeforeDelete = await recipeeComponentsPage.countDeleteButtons();
        await recipeeComponentsPage.clickOnLastDeleteButton();

        recipeeDeleteDialog = new RecipeeDeleteDialog();
        expect(await recipeeDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Recipee?');
        await recipeeDeleteDialog.clickOnConfirmButton();

        expect(await recipeeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
