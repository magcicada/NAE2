package co.neeve.nae2.mixin.voidcell;

import appeng.api.config.Actionable;
import appeng.api.networking.security.IActionSource;
import appeng.api.storage.ICellHandler;
import appeng.api.storage.data.IAEStack;
import appeng.me.storage.DriveWatcher;
import co.neeve.nae2.common.interfaces.IVoidingCellHandler;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = DriveWatcher.class, remap = false)
public class MixinDriveWatcher {
    @Shadow @Final private ICellHandler handler;

    @Inject(method = "injectItems",
            at = @At(
                    value = "INVOKE",
                    target = "Lappeng/me/helpers/AENetworkProxy;isActive()Z"),
            cancellable = true)
    @SuppressWarnings("rawtypes")
    private void wrapInstanceOfCheckInjectItems(final IAEStack input,
                                                final Actionable type,
                                                final IActionSource src,
                                                final CallbackInfoReturnable<IAEStack> cir,
                                                @Local(name = "remainder") IAEStack remainder) {
        if (handler instanceof IVoidingCellHandler) {
            cir.setReturnValue(remainder);
        }
    }

    @Inject(method = "extractItems",
            at = @At(
                    value = "INVOKE",
                    target = "Lappeng/me/helpers/AENetworkProxy;isActive()Z"),
            cancellable = true)
    @SuppressWarnings("rawtypes")
    private void wrapInstanceOfCheckExtractItems(final IAEStack input,
                                                 final Actionable type,
                                                 final IActionSource src,
                                                 final CallbackInfoReturnable<IAEStack> cir,
                                                 @Local(name = "extractable") IAEStack extractable) {
        if (handler instanceof IVoidingCellHandler) {
            cir.setReturnValue(extractable);
        }
    }
}
