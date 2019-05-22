import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CraftBeerStoreSharedModule } from 'app/shared';
import { RESUME_ROUTE, ResumeComponent } from './';

@NgModule({
    imports: [CraftBeerStoreSharedModule, RouterModule.forRoot([RESUME_ROUTE], { useHash: true })],
    declarations: [ResumeComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CraftBeerStoreResumeModule {}
