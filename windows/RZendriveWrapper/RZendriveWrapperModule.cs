using ReactNative.Bridge;
using System;
using System.Collections.Generic;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;

namespace Com.Reactlibrary.RZendriveWrapper
{
    /// <summary>
    /// A module that allows JS to share data.
    /// </summary>
    class RZendriveWrapperModule : NativeModuleBase
    {
        /// <summary>
        /// Instantiates the <see cref="RZendriveWrapperModule"/>.
        /// </summary>
        internal RZendriveWrapperModule()
        {

        }

        /// <summary>
        /// The name of the native module.
        /// </summary>
        public override string Name
        {
            get
            {
                return "RZendriveWrapper";
            }
        }
    }
}
