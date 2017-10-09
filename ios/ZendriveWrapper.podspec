#
# Be sure to run `pod lib lint ZendriveWrapper.podspec' to ensure this is a
# valid spec before submitting.
#
# Any lines starting with a # are optional, but their use is encouraged
# To learn more about a Podspec see http://guides.cocoapods.org/syntax/podspec.html
#

Pod::Spec.new do |s|
  s.name             = 'ZendriveWrapper'
  s.version          = '0.1.1'
  s.summary          = "Zendrive react-native wrapper"
  s.description      = "ObjectiveC Zendrive react-native wrapper"
  s.homepage         = 'https://github.com' # 'https://github.com/banduk/ZendriveWrapper'
  s.license          = { :type => 'MIT', :file => 'LICENSE' }
  s.author           = { 'Mauricio Banduk' => 'banduk@gmail.com' }
  s.source           = { :git => 'https://github.com/banduk/ZendriveWrapper.git', :tag => s.version.to_s }
  s.ios.deployment_target = '8.0'
  s.source_files = 'ZendriveWrapper/**/*.{h,m}'
  # s.dependency 'Yoga'
  # s.dependency 'React'
  # s.dependency 'ZendriveSDK'
end
